package com.cituojt.happyTicketingApi.responses.projects;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.cituojt.happyTicketingApi.entities.ProjectMember;
import com.cituojt.happyTicketingApi.entities.Task;

public class ProjectDetailsJSON {

    private UUID id;
    private String name;
    private Set<UserJSON> members;
    private Set<TaskJSON> tasks;
    private String channelName;

    public ProjectDetailsJSON(UUID id, String name, Set<ProjectMember> members, Set<Task> tasks, String channelName) {
        this.setId(id);
        this.setName(name);
        this.setChannelName(channelName);
        this.members = processMembers(members);
        this.tasks = processTasks(tasks);
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    private Set<UserJSON> processMembers(Set<ProjectMember> members) {
        Set<UserJSON> membersJSON = new HashSet<>();
        for (ProjectMember m : members) {
            UserJSON userJSON = new UserJSON(m.getUser().getId(), m.getUser().getEmail(), this.id);
            membersJSON.add(userJSON);
        }
        return membersJSON;
    }

    private Set<TaskJSON> processTasks(Set<Task> tasks) {
        Set<TaskJSON> tasksJSON = new HashSet<>();
        for (Task t : tasks) {
            TaskJSON taskJSON = new TaskJSON(t.getId(), this.id, t.getName(), t.getAssignedTo(), t.getStatus(),
                    t.getEstimatedTime());
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
