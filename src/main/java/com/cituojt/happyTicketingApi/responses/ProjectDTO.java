package com.cituojt.happyTicketingApi.responses;

import java.util.Collection;

public class ProjectDTO {

    private int id;
    private Collection<UserDTO> members;
    private UserDTO owner;
    private Collection<TicketDTO> tickets;

    public ProjectDTO() {
    }

    public ProjectDTO(int id, Collection<UserDTO> members, UserDTO owner, Collection<TicketDTO> tickets) {
        this.id = id;
        this.members = members;
        this.owner = owner;
        this.tickets = tickets;
    }

    public Collection<TicketDTO> getTickets() {
        return tickets;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public Collection<UserDTO> getMembers() {
        return members;
    }

    public int getId() {
        return id;
    }

}
