package com.cituojt.happyTicketingApi.controllers;

import com.cituojt.happyTicketingApi.responses.ProjectsResponse;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan(basePackages = "happyTicketingApi.controllers")
public class ProjectsController {

    @RequestMapping(value = "/api/v1/projects", produces = "application/json", method = RequestMethod.GET)
    public ResponseEntity<ProjectsResponse> getProjectsForUser(HttpServletRequest req, HttpServletResponse res) {

        return ResponseEntity.ok(new ProjectsResponse(new ArrayList<>()));
    }

}
