package com.cituojt.happyTicketingApi.responses.projects;

import java.util.HashSet;
import java.util.Set;
import com.cituojt.happyTicketingApi.entities.ProjectMember;
import com.cituojt.happyTicketingApi.entities.Task;

public class ProjectDetailsJSON {

    private Long id;
    private String name;
    private Set<UserJSON> members;
    private Set<TaskJSON> tasks;

    public ProjectDetailsJSON(Long id, String name, Set<ProjectMember> members, Set<Task> tasks) {
        this.setId(id);
        this.setName(name);
        this.members = processMembers(members);
        this.tasks = processTasks(tasks);
    }

    private Set<UserJSON> processMembers(Set<ProjectMember> members) {
        Set<UserJSON> membersJSON = new HashSet<>();
        for (ProjectMember m : members) {
            UserJSON userJSON = new UserJSON(m.getUser().getId(), m.getUser().getEmail());
            membersJSON.add(userJSON);
        }
        return membersJSON;
    }

    private Set<TaskJSON> processTasks(Set<Task> tasks) {
        Set<TaskJSON> tasksJSON = new HashSet<>();
        for (Task t : tasks) {
            TaskJSON taskJSON= new TaskJSON(t.getId(), t.getName(), t.getAssignedTo(), t.getStatus());
            tasksJSON.add(taskJSON);
        }
        return tasksJSON;
    }

    public Set<UserJSON> getMembers() {
        return members;
    }

    public Set<TaskJSON> getTasks() {
        return tasks;
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
