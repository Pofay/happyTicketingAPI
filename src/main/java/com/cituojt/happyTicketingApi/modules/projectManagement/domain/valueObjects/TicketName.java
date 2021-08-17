package com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TicketName {

    @NonNull
    private final String ticketName;

    public static TicketName of(String ticketName) {
        return new TicketName(ticketName);
    }

}
