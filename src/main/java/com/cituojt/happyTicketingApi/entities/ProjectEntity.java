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
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "project")
public class ProjectEntity {

    @Id
    @Column(name = "project_id")
    @org.hibernate.annotations.Type(type = "org.hibernate.type.PostgresUUIDType")
    private UUID id;

    @Column(name = "project_name")
    private String name;

    @Column(name = "channel_id")
    private String channelId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectMemberEntity> members = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskEntity> tasks = new HashSet<>();

    public ProjectEntity() {
    }

    public ProjectEntity(UUID id, String name, UUID channelId) {
        this.id = id;
        this.name = name.replace(' ', '-').replace(':', ';');
        this.channelId = channelId.toString();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Set<ProjectMemberEntity> getMembers() {
        return members;
    }

    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    public void addMember(UserEntity user, String role) {
        ProjectMemberEntity m = new ProjectMemberEntity(user, this, role);
        members.add(m);
    }

    public void addTask(String name, String email, String status, Integer estimatedTime) {
        TaskEntity t = new TaskEntity(UUID.randomUUID(), name, email, status, estimatedTime);
        tasks.add(t);
        t.setProject(this);
    }

    public void addTask(TaskEntity t) {
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
    public Optional<TaskEntity> getTaskbyTaskId(String id) {
        for (TaskEntity k : tasks) {
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
        ProjectEntity other = (ProjectEntity) obj;
        return Objects.equals(id, other.id);
    }

    public void deleteTask(TaskEntity t) {
        tasks.remove(t);
        t.setProject(null);
    }

}
