package com.cituojt.happyTicketingApi.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "oauthId")
    private String oauthId;

    private String email;

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
}
