package com.cituojt.happyTicketingApi.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ComponentScan(basePackages = "happyTicketingApi.controllers")
public class ProjectsController {

    @RequestMapping(value = "/api/v1/projects", produces = "application/json", method = RequestMethod.GET)
    @ResponseBody
    public String getProjectsForUser(HttpServletRequest req, HttpServletResponse res) {

        return "";
    }

}
