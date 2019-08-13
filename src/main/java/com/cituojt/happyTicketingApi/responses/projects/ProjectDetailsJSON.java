package com.cituojt.happyTicketingApi.responses.projects;

import java.util.ArrayList;
import java.util.List;
import com.cituojt.happyTicketingApi.entities.ProjectMember;

public class ProjectDetailsJSON {

    private Long id;
    private String name;
    private List<UserJSON> members;

    public ProjectDetailsJSON(Long id, String name, List<ProjectMember> members) {
        this.setId(id);
        this.setName(name);
        this.members = processMembers(members);
    }

    private List<UserJSON> processMembers(List<ProjectMember> members) {
        List<UserJSON> membersJSON = new ArrayList<>();
        for (ProjectMember m : members) {
            UserJSON userJSON = new UserJSON(m.getUser().getId(), m.getUser().getEmail());
            membersJSON.add(userJSON);
        }
        return membersJSON;
    }

    public List<UserJSON> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
