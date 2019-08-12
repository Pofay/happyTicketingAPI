package com.cituojt.happyTicketingApi.responses.projects;

import java.util.List;
import java.util.stream.Collectors;
import com.cituojt.happyTicketingApi.entities.ProjectMember;
import com.cituojt.happyTicketingApi.entities.Task;

public class ProjectDetailsJSON {

    private Long id;
    private String name;
    private List<UserJSON> members;
    private List<TaskJSON> tasks;

    public ProjectDetailsJSON(Long id, String name, List<ProjectMember> members, List<Task> tasks) {
        this.setId(id);
        this.setName(name);
        this.members = processMembers(members);
        this.tasks = convertToJSON(tasks);
    }

    private List<UserJSON> processMembers(List<ProjectMember> members) {
        return members.stream().map(m -> new UserJSON(m.getUser().getId(), m.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    private List<TaskJSON> convertToJSON(List<Task> tasks) {
        return tasks.stream().map(t -> new TaskJSON(t.getId(), t.getName(), t.getAssignedTo(), t.getStatus()))
                .collect(Collectors.toList());
    }

    public List<UserJSON> getMembers() {
        return this.members;
    }

    public List<TaskJSON> getTasks() {
        return this.tasks;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
