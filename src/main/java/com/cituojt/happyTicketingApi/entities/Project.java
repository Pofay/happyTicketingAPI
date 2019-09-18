package com.cituojt.happyTicketingApi.entities;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue
    private Long projectId;

    private String name;

    private String channelId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<ProjectMember> members = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();


    public Project() {
    }

    public Project(String name, UUID channelId) {
        this.name = name;
        this.channelId = channelId.toString();
    }

    public Long getId() {
        return projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ProjectMember> getMembers() {
        return members;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void addMember(User user, String role) {
        ProjectMember m = new ProjectMember(user, this, role);
        members.add(m);
    }

    public void addTask(String name, String email, String status) {
        Task t = new Task(name, email, status);
        tasks.add(t);
        t.setProject(this);
    }

    public void addTask(Task t) {
        tasks.add(t);
        t.setProject(this);
    }
    // Add removal option

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return String.format("%s@%s", channelId, name);
    }

    // get Task by ID
    public Optional<Task> getTaskbyTaskId(Long id) {
        for (Task k : tasks) {
            if (k.getId() == id)
                return Optional.of(k);
        }
        return Optional.of(null);
    }
}
