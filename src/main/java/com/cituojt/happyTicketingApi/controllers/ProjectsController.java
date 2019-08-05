package com.cituojt.happyTicketingApi.controllers;

import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;
import com.cituojt.happyTicketingApi.responses.ProjectDTO;
import com.cituojt.happyTicketingApi.responses.ProjectsResponse;
import com.cituojt.happyTicketingApi.responses.TicketDTO;
import com.cituojt.happyTicketingApi.responses.UserDTO;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(value = "/api/v1/projects", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<ProjectsResponse> getProjectsForUser(HttpServletRequest req, HttpServletResponse res) {

        UserDTO userDTO = new UserDTO("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");
        List<UserDTO> users = new ArrayList<>();
        users.add(userDTO);
        ProjectDTO projectDTO = new ProjectDTO(Long.valueOf(1), "ProjectM", users, new ArrayList<TicketDTO>());
        List<ProjectDTO> projects = new ArrayList<>();
        projects.add(projectDTO);

        return ResponseEntity.ok(new ProjectsResponse(projects));
    }

}
