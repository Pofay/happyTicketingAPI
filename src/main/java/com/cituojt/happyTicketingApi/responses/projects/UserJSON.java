package com.cituojt.happyTicketingApi.responses.projects;

public class UserJSON {

    private Long id;
    private String email;

    public UserJSON(Long id, String email) {
        this.setId(id);
        this.setEmail(email);
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

    public void setId(Long id) {
        this.id = id;
    }
}
