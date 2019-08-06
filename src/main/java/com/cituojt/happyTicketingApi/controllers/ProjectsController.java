package com.cituojt.happyTicketingApi.controllers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;
import com.cituojt.happyTicketingApi.responses.projects.IndexResponse;
import com.cituojt.happyTicketingApi.responses.projects.ProjectJSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = "happyTicketingApi.controllers")
public class ProjectsController {

    private UserRepository userRepo;
    private ProjectRepository projectRepo;

    @Autowired
    public ProjectsController(UserRepository userRepo, ProjectRepository projectRepo) {
        this.userRepo = userRepo;
        this.projectRepo = projectRepo;
    }

    @RequestMapping(value = "/api/v1/projects", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<IndexResponse> getProjectsForUser(HttpServletRequest req, HttpServletResponse res) {
        String token = req.getHeader("Authorization").substring("Bearer ".length());
        System.out.println(token);
        DecodedJWT jwt = JWT.decode(token);
        String subClaim = jwt.getClaim("sub").asString();

        User u = userRepo.findByOAuthId(subClaim);

        Iterable<Project> projects = this.projectRepo.getProjectsForUser(u.getId());
        List<ProjectJSON> jsonResponse = new ArrayList<>();

        for (Project p : projects) {
            ProjectJSON transformed = new ProjectJSON(p.getId(), p.getName(), "/v1/projects",
                    Arrays.asList("GET", "POST"));
            jsonResponse.add(transformed);
        }

        IndexResponse response = new IndexResponse(jsonResponse);

        return ResponseEntity.ok(response);
    }

}
