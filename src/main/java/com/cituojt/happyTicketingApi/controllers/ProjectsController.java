package com.cituojt.happyTicketingApi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;
import com.cituojt.happyTicketingApi.requests.AddMemberRequest;
import com.cituojt.happyTicketingApi.requests.CreateProjectRequest;
import com.cituojt.happyTicketingApi.requests.CreateTaskRequest;
import com.cituojt.happyTicketingApi.responses.projects.IndexResponse;
import com.cituojt.happyTicketingApi.responses.projects.ProjectDetailsJSON;
import com.cituojt.happyTicketingApi.responses.projects.ProjectJSON;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectsController {

    private UserRepository userRepo;
    private ProjectRepository projectRepo;

    @Autowired
    public ProjectsController(UserRepository userRepo, ProjectRepository projectRepo) {
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
    }

    @GetMapping(value = "/api/v1/projects", produces = "application/json")
    public ResponseEntity getProjectsForUser(HttpServletRequest req, HttpServletResponse res) {
        String oauthId = getOauthIdFromRequest(req);

        Optional<User> userOrNull = userRepo.findByOAuthId(oauthId);

        if (userOrNull.isPresent()) {
            User u = userOrNull.get();
            return ResponseEntity.ok(constructResponse(u));
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
            Project project = projectOrNull.get();
            ProjectDetailsJSON payload = new ProjectDetailsJSON(project.getId(), project.getName(),
                    project.getMembers(), project.getTasks());

            return ResponseEntity.ok(payload);
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
            Project p = new Project(body.getName());
            p.addMember(u, "OWNER");

            projectRepo.save(p);

            ProjectDetailsJSON payload =
                    new ProjectDetailsJSON(p.getId(), p.getName(), p.getMembers(), p.getTasks());

            return ResponseEntity.status(201).body(payload);
        }

        else {
            return ResponseEntity.status(404).build();
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

            p.addTask(body.getName(), u.getEmail(), "TO IMPLEMENT");

            projectRepo.save(p);

            ProjectDetailsJSON payload =
                    new ProjectDetailsJSON(p.getId(), p.getName(), p.getMembers(), p.getTasks());

            return ResponseEntity.status(201).body(payload);
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

                ProjectDetailsJSON payload = new ProjectDetailsJSON(p.getId(), p.getName(),
                        p.getMembers(), p.getTasks());

                return ResponseEntity.status(201).body(payload);
            } else {
                JSONObject errorPayload = new JSONObject();
                errorPayload.put("error", "email is not yet registered to system.");
                return ResponseEntity.status(403).body(errorPayload.toString());
            }

        } else {
            return ResponseEntity.status(400).build();
        }
    }

    private IndexResponse constructResponse(User u) {
        Iterable<Project> projects = this.projectRepo.getProjectsForUser(u.getId());
        List<ProjectJSON> jsonResponse = new ArrayList<>();
        for (Project p : projects) {
            ProjectJSON json = new ProjectJSON(p.getId(), p.getName(), "/v1/projects",
                    Arrays.asList("GET", "POST"));
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
