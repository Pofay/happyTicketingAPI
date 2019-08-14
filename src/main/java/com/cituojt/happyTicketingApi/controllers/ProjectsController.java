package com.cituojt.happyTicketingApi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/api/v1/projects", produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity getProjectsForUser(HttpServletRequest req, HttpServletResponse res) {
        String oauthId = getOauthIdFromRequest(req);

        User u = userRepo.findByOAuthId(oauthId);

        return ResponseEntity.ok(constructResponse(u));
    }

    @RequestMapping(value = "/api/v1/projects/{id}", produces = "application/json",
            method = RequestMethod.GET)
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

    @RequestMapping(value = "/api/v1/projects", produces = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity createProject(@RequestParam("name") String name, HttpServletRequest req,
            HttpServletResponse res) {

        if (name.isEmpty()) {
            JSONObject payload = new JSONObject();
            payload.put("error", "name cannot be empty");
            return ResponseEntity.status(400).body(payload.toString());
        }

        String oauthId = getOauthIdFromRequest(req);

        User u = userRepo.findByOAuthId(oauthId);

        Project p = new Project(name);
        p.addMember(u, "OWNER");

        projectRepo.save(p);

        ProjectDetailsJSON payload =
                new ProjectDetailsJSON(p.getId(), p.getName(), p.getMembers(), p.getTasks());

        return ResponseEntity.status(201).body(payload);
    }

    @RequestMapping(value = "/api/v1/projects/{id}/tasks", method = RequestMethod.POST)
    public ResponseEntity addTaskToProject(@PathVariable("id") long id,
            @RequestParam("name") String name, HttpServletRequest req, HttpServletResponse res) {

        String oauthId = getOauthIdFromRequest(req);

        User u = userRepo.findByOAuthId(oauthId);
        Optional<Project> projectOrNull = projectRepo.findById(Long.valueOf(id));

        if (projectOrNull.isPresent()) {
            Project p = projectOrNull.get();
            p.addTask(name, u.getEmail(), "TO IMPLEMENT");

            projectRepo.save(p);

            ProjectDetailsJSON payload =
                    new ProjectDetailsJSON(p.getId(), p.getName(), p.getMembers(), p.getTasks());

            return ResponseEntity.status(201).body(payload);
        } else {
            return ResponseEntity.badRequest().build();
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
