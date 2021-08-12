package com.cituojt.happyTicketingApi.entities;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @Column(name = "task_id", nullable = false)
    private String id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(name = "task_name")
    private String name;

    private String assignedTo;

    @Column(name = "task_status")
    private String status;

    private Integer estimatedTime;

    public TaskEntity() {

    }

    public TaskEntity(UUID id, String name, String assignedTo, String status, Integer estimatedTime) {
        this.id = id.toString();
        this.name = name;
        this.assignedTo = assignedTo;
        this.status = status;
        this.setEstimatedTime(estimatedTime);
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProject(ProjectEntity p) {
        this.project = p;
    }

    public ProjectEntity getProject() {
        return project;
    }
}
