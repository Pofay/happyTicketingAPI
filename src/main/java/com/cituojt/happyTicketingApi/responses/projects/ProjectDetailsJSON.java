package com.cituojt.happyTicketingApi.responses.projects;

import java.util.List;
import java.util.stream.Collectors;
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
        return members.stream().map(m -> new UserJSON(m.getUser().getId(), m.getUser().getEmail()))
                .collect(Collectors.toList());
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
