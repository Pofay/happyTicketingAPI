package com.cituojt.happyTicketingApi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.Task;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;
import com.cituojt.happyTicketingApi.requests.AddMemberRequest;
import com.cituojt.happyTicketingApi.requests.CreateProjectRequest;
import com.cituojt.happyTicketingApi.requests.CreateTaskRequest;
import com.cituojt.happyTicketingApi.requests.UpdateTaskRequest;
import com.cituojt.happyTicketingApi.responses.projects.IndexResponse;
import com.cituojt.happyTicketingApi.responses.projects.ProjectDetailsJSON;
import com.cituojt.happyTicketingApi.responses.projects.TaskJSON;
import com.cituojt.happyTicketingApi.responses.projects.UserJSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectsController {

    private UserRepository userRepo;
    private ProjectRepository projectRepo;
    private RealtimeEmitter emitter;

    @Autowired
    public ProjectsController(UserRepository userRepo, ProjectRepository projectRepo,
            RealtimeEmitter emitter) {
        this.userRepo = userRepo;
        this.emitter = emitter;
        this.projectRepo = projectRepo;
    }

    @GetMapping(value = "/api/v1/projects", produces = "application/json")
    public ResponseEntity getProjectsForUser(HttpServletRequest req, HttpServletResponse res) {
        String oauthId = getOauthIdFromRequest(req);

        Optional<User> userOrNull = userRepo.findByOAuthId(oauthId);

        if (userOrNull.isPresent()) {
            User u = userOrNull.get();
            Iterable<Project> projects = projectRepo.getProjectsForUser(u.getId());
            return ResponseEntity.ok(mapProjectsToJson(projects));
        } else {
            JSONObject errorPayload = new JSONObject();
            errorPayload.put("error", "access_token user is not yet registered or doesn't exist.");
            return ResponseEntity.status(403).body(errorPayload.toString());
        }
    }

    @GetMapping(value = "/api/v1/projects/{id}", produces = "application/json")
    public ResponseEntity getProjectForUserById(@PathVariable("id") long id, HttpServletRequest req,
            HttpServletResponse res) throws Exception {

        Optional<Project> projectOrNull = projectRepo.findById(Long.valueOf(id));

        if (projectOrNull.isPresent()) {
            return ResponseMapper.mapProjectToJson(projectOrNull.get(), 200);
        } else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/api/v1/projects", produces = "application/json")
    public ResponseEntity createProject(@RequestBody CreateProjectRequest body,
            HttpServletRequest req, HttpServletResponse res) {

        if (body.getName().isEmpty()) {
            JSONObject payload = new JSONObject();
            payload.put("error", "name cannot be empty");
            return ResponseEntity.status(400).body(payload.toString());
        }

        String oauthId = getOauthIdFromRequest(req);

        Optional<User> userOrNull = userRepo.findByOAuthId(oauthId);

        if (userOrNull.isPresent()) {
            User u = userOrNull.get();
            Project p = new Project(body.getName(), UUID.randomUUID());
            p.addMember(u, "OWNER");

            projectRepo.save(p);

            return ResponseMapper.mapProjectToJson(p, 201);
        }

        else {
            return ResponseEntity.status(403).build();
        }
    }

    @PostMapping(value = "/api/v1/projects/{id}/tasks", produces = "application/json")
    public ResponseEntity addTaskToProject(@PathVariable("id") long id,
            @RequestBody CreateTaskRequest body, HttpServletRequest req, HttpServletResponse res) {

        String oauthId = getOauthIdFromRequest(req);

        Optional<User> userOrNull = userRepo.findByOAuthId(oauthId);
        Optional<Project> projectOrNull = projectRepo.findById(Long.valueOf(id));

        if (projectOrNull.isPresent() && userOrNull.isPresent()) {
            User u = userOrNull.get();
            Project p = projectOrNull.get();
            Task t = new Task(UUID.randomUUID(), body.getName(), u.getEmail(), body.getStatus(),
                    body.getEstimatedTime());

            p.addTask(t);

            projectRepo.save(p);

            TaskJSON taskAddedPayload = new TaskJSON(t.getId(), p.getId(), t.getName(),
                    t.getAssignedTo(), t.getStatus(), t.getEstimatedTime());

            emitter.emit(p.getChannelName(), "task-added", taskAddedPayload);

            return ResponseMapper.mapProjectToJson(p, 201);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/api/v1/projects/{id}/members", produces = "application/json")
    public ResponseEntity addMemberToProject(@RequestBody AddMemberRequest body,
            @PathVariable("id") long id, HttpServletRequest req, HttpServletResponse res) {

        Optional<Project> projectOrNull = projectRepo.findById(Long.valueOf(id));

        if (projectOrNull.isPresent()) {
            Project p = projectOrNull.get();
            Optional<User> userOrNull = userRepo.findByEmail(body.getMemberEmail());
            if (userOrNull.isPresent()) {
                User u = userOrNull.get();
                p.addMember(u, "MEMBER");
                projectRepo.save(p);

                UserJSON payload = new UserJSON(u.getId(), u.getEmail(), p.getId());
                emitter.emit(p.getChannelName(), "member-added", payload);

                return ResponseMapper.mapProjectToJson(p, 201);
            } else {
                JSONObject errorPayload = new JSONObject();
                errorPayload.put("error", "email is not yet registered to system.");
                return ResponseEntity.status(403).body(errorPayload.toString());
            }

        } else {
            return ResponseEntity.status(400).build();
        }
    }

    @PutMapping(value = "/api/v1/projects/{id}/tasks", produces = "application/json")
    public ResponseEntity updateTask(@PathVariable("id") long id,
            @RequestBody UpdateTaskRequest body, HttpServletRequest req, HttpServletResponse res) {
        Optional<Project> projectOrNull = projectRepo.findById(Long.valueOf(id));

        if (projectOrNull.isPresent()) {
            Project p = projectOrNull.get();

            Optional<Task> taskOrNull = p.getTaskbyTaskId(body.getId());
            // check if task is in the project
            if (taskOrNull.isPresent()) {
                Task t = taskOrNull.get();
                t.setName(body.getName());
                t.setAssignedTo(body.getAssignedTo());
                t.setStatus(body.getStatus());
                projectRepo.save(p);
                TaskJSON payload = new TaskJSON(t.getId(), p.getId(), t.getName(),
                        t.getAssignedTo(), t.getStatus(), t.getEstimatedTime());
                emitter.emit(p.getChannelName(), "task-updated", payload);
                return ResponseEntity.status(200).body(payload);
            } else
                return ResponseEntity.status(403).body("Task Not Found In Project!");
        } else
            return ResponseEntity.status(403).build();
    }

    private IndexResponse mapProjectsToJson(Iterable<Project> projects) {
        List<ProjectDetailsJSON> jsonResponse = new ArrayList<>();
        for (Project p : projects) {
            ProjectDetailsJSON json = new ProjectDetailsJSON(p.getId(), p.getName(), p.getMembers(),
                    p.getTasks(), p.getChannelName());
            jsonResponse.add(json);
        }

        return new IndexResponse(jsonResponse);
    }

    private String getOauthIdFromRequest(HttpServletRequest req) {
        String token = req.getHeader("Authorization").substring("Bearer ".length());
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("sub").asString();
    }
}
