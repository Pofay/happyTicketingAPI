package com.cituojt.happyTicketingApi.responses;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;

public class ProjectDTO {

    private Long id;
    private List<UserDTO> members;
    private List<TicketDTO> tickets;
    private String name;

    public ProjectDTO() {
    }

    public ProjectDTO(Long id, String name, List<UserDTO> members, List<TicketDTO> tickets) {
        this.id = id;
        this.name = name;
        this.members = members;
        this.tickets = tickets;
    }

    public List<TicketDTO> getTickets() {
        return tickets;
    }

    public List<UserDTO> getMembers() {
        return members;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        ProjectDTO that = (ProjectDTO) o;
        return Objects.equals(id, that.id) && CollectionUtils.containsAll(tickets, that.tickets)
                && CollectionUtils.containsAll(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, members, tickets);
    }
}
