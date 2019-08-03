package com.cituojt.happyTicketingApi.responses;

public class TicketDTO {
    private Long id;
    private String name;
    private Long assignedTo;
    private String status;

    public TicketDTO() {
    }

    public TicketDTO(Long id, String name, Long assignedTo, String status) {
        this.id = id;
        this.name = name;
        this.assignedTo = assignedTo;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public String getStatus() {
        return status;
    }

}
