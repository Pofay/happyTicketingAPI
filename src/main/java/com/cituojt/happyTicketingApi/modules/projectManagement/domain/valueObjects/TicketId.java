package com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TicketId {

    @NonNull
    private final UUID value;

    public static TicketId fromUUID(UUID id) {
        return new TicketId(id);
    }

}
