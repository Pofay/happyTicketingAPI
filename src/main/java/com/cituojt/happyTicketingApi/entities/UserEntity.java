package com.cituojt.happyTicketingApi.entities;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "project_user")
public class UserEntity {

    @Id
    @org.hibernate.annotations.Type(type = "org.hibernate.type.PostgresUUIDType")
    @Column(name = "user_id")
    private UUID id;

    private String oauthId;

    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<ProjectMemberEntity> memberShips;

    public UserEntity() {
    }

    public UserEntity(UUID id, String email, String oAuthId) {
        this.id = id;
        this.oauthId = oAuthId;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    public String getOAuthId() {
        return oauthId;
    }

    public void setOAuthId(String oAuthId) {
        this.oauthId = oAuthId;
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
        UserEntity other = (UserEntity) obj;
        return Objects.equals(id, other.id);
    }

    public Iterable<ProjectMemberEntity> getMemberships() {
        return memberShips;
    }

}
