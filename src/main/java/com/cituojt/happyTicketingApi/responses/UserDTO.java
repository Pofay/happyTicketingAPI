package com.cituojt.happyTicketingApi.responses;

public class UserDTO {
    private int id;
    private String name;

    public UserDTO() {
    }

    public UserDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}
