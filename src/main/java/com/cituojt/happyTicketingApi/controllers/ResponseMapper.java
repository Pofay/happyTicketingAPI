package com.cituojt.happyTicketingApi.controllers;

import com.cituojt.happyTicketingApi.entities.ProjectEntity;
import com.cituojt.happyTicketingApi.responses.projects.ProjectDetailsJSON;
import org.springframework.http.ResponseEntity;

public class ResponseMapper {

    public static ResponseEntity mapProjectToJson(ProjectEntity p, int status) {
        ProjectDetailsJSON json = new ProjectDetailsJSON(p.getId(), p.getName(), p.getMembers(), p.getTasks(),
                p.getChannelName());
        return ResponseEntity.status(status).body(json);
    }
}
