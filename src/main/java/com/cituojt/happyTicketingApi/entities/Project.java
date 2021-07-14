package com.cituojt.happyTicketingApi.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class Project {

    @Id
    @Column(name = "project_id")
    @GeneratedValue(generator = "project_id_generator")
    @SequenceGenerator(name = "project_id_generator", sequenceName = "project_project_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "project_name")
    private String name;

    @Column(name = "channel_id")
    private String channelId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectMember> members = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    public Project() {
    }

    public Project(String name, UUID channelId) {
        this.name = name.replace(' ', '-').replace(':', ';');
        this.channelId = channelId.toString();
    }

    public Long getId() {
        return id;
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

    public void addTask(String name, String email, String status, Integer estimatedTime) {
        Task t = new Task(UUID.randomUUID(), name, email, status, estimatedTime);
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
    public Optional<Task> getTaskbyTaskId(String id) {
        for (Task k : tasks) {
            if (k.getId().equals(id))
                return Optional.of(k);
        }
        return Optional.of(null);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Project other = (Project) obj;
        return Objects.equals(id, other.id);
    }

    public void deleteTask(Task t) {
        tasks.remove(t);
        t.setProject(null);
    }

}
