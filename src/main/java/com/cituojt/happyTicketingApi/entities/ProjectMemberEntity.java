package com.cituojt.happyTicketingApi.entities;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity(name = "ProjectMember")
@Table(name = "project_member")
public class ProjectMemberEntity {

    @EmbeddedId
    private ProjectMemberId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_user_id")
    @MapsId("user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_project_id")
    @MapsId("project_id")
    private ProjectEntity project;

    @Column(name = "member_role")
    private String role;

    @Column(name = "member_status")
    private String status;

    public ProjectMemberEntity() {
    }

    public ProjectMemberEntity(UserEntity u, ProjectEntity p, String role) {
        this.user = u;
        this.project = p;
        this.id = new ProjectMemberId(p.getId(), u.getId());
        this.role = role;
        this.status = "ACTIVE";
    }

    public String getStatus() {
        return status;
    }

    public void disable() {
        this.status = "DISABLED";
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity u) {
        this.user = u;
    }

    public ProjectEntity getProject() {
        return project;
    }

    public void setProject(ProjectEntity p) {
        this.project = p;
    }

    public ProjectMemberId getId() {
        return id;
    }

    public void setProjectMemberId(ProjectMemberId id) {
        this.id = id;
    }

    public void setProjectMemberId(UUID projectId, UUID userId) {
        this.id = new ProjectMemberId(projectId, userId);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
        ProjectMemberEntity other = (ProjectMemberEntity) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return String.format("ProjectId: %d, UserId: %d", project.getId(), user.getId());
    }
}
