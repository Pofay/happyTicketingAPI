package com.cituojt.happyTicketingApi.entities;

import java.util.Objects;
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
public class ProjectMember {

    @EmbeddedId
    private ProjectMemberId id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId(value = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId(value = "project_id")
    private Project project;

    @Column(name = "member_role")
    private String role;

    @Column(name = "member_status")
    private String status;

    public ProjectMember() {
    }

    public ProjectMember(User u, Project p, String role) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User u) {
        this.user = u;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project p) {
        this.project = p;
    }

    public ProjectMemberId getId() {
        return id;
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
        ProjectMember other = (ProjectMember) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return String.format("ProjectId: %d, UserId: %d", project.getId(), user.getId());
    }
}
