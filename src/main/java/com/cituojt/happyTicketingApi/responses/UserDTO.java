package com.cituojt.happyTicketingApi.responses;

import java.util.Objects;

public class UserDTO {
    private String id;
    private String email;

    public UserDTO() {
    }

    public UserDTO(String id, String email) {
        this.id = id;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        UserDTO that = (UserDTO) o;
        return Objects.equals(email, that.email) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

}
