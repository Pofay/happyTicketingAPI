package com.cituojt.happyTicketingApi.entities;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectMember> members = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.projectId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProjectMember> getMembers() {
        return this.members;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void addMember(User user, String role) {
        ProjectMember m = new ProjectMember(user, this, role);
        this.members.add(m);
    }

    public void addTask(String taskName, String userEmail, String status) {
        Task t = new Task(taskName, userEmail, status);
        this.tasks.add(t);
    }

    // Add removal option

}
