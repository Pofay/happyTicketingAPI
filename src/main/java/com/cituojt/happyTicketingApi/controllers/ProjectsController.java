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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/api/v1/projects", produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity getProjectsForUser(HttpServletRequest req,
            HttpServletResponse res) {
        String token = req.getHeader("Authorization").substring("Bearer ".length());
        DecodedJWT jwt = JWT.decode(token);
        String subClaim = jwt.getClaim("sub").asString();

        User u = userRepo.findByOAuthId(subClaim);

        return ResponseEntity.ok(constructResponse(u));
    }

    @RequestMapping(value = "/api/v1/projects/{id}", produces = "application/json",
            method = RequestMethod.GET)
    public ResponseEntity getProjectForUserById(@PathVariable("id") long id,
            HttpServletRequest req, HttpServletResponse res) throws Exception {

        Optional<Project> projectOrNull = projectRepo.findById(Long.valueOf(id));

        if (projectOrNull.isPresent()) {
            Project project = projectOrNull.get();
            ProjectDetailsJSON payload = new ProjectDetailsJSON(project.getId(), project.getName(),
                    project.getMembers());

            return ResponseEntity.ok(payload);
        } else
            return ResponseEntity.notFound().build();
    }

    private IndexResponse constructResponse(User u) {
        Iterable<Project> projects = this.projectRepo.getProjectsForUser(u.getId());
        List<ProjectJSON> jsonResponse = StreamSupport
                .stream(projects.spliterator(), true).map(p -> new ProjectJSON(p.getId(),
                        p.getName(), "/v1/projects", Arrays.asList("GET", "POST")))
                .collect(Collectors.toList());

        return new IndexResponse(jsonResponse);
    }

}
