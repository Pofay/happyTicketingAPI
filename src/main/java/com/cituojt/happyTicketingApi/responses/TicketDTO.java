package com.cituojt.happyTicketingApi.responses;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TicketDTO that = (TicketDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(status, that.status)
                && Objects.equals(assignedTo, that.assignedTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status, name, assignedTo);
    }

}
