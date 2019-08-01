package com.cituojt.happyTicketingApi.responses;

public class TicketDTO {
    private int id;
    private String name;
    private int assignedTo;
    private String status;

    public TicketDTO() {
    }

    public TicketDTO(int id, String name, int assignedTo, String status) {
        this.id = id;
        this.name = name;
        this.assignedTo = assignedTo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public String getStatus() {
        return status;
    }

}
