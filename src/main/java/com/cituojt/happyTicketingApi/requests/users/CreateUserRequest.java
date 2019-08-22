package com.cituojt.happyTicketingApi.requests.users;

public class CreateUserRequest {

    private String userId;
    private String email;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String userId, String email) {
        this.setUserId(userId);
        this.setEmail(email);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
