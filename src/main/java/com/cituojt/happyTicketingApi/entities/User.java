package com.cituojt.happyTicketingApi.entities;

import java.util.Objects;
import java.util.Set;

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
@Table(name = "project_user")
public class User {

    @Id
    @GeneratedValue(generator = "user_id_generator")
    @SequenceGenerator(name = "user_id_generator", sequenceName = "project_user_user_id_seq", allocationSize = 1)
    @Column(name = "user_id")
    private Long id;

    private String oauthId;

    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<ProjectMember> memberShips;

    public User() {
    }

    public User(String email, String oAuthId) {
        this.oauthId = oAuthId;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
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
        User other = (User) obj;
        return Objects.equals(id, other.id);
    }

    public Iterable<ProjectMember> getMemberships() {
        return memberShips;
    }

}
