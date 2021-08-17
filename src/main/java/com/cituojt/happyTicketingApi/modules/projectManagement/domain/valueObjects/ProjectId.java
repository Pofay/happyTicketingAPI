package com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class ProjectId {

    @NonNull
    private final UUID value;

    public static ProjectId fromUUID(UUID uuid) {
        return new ProjectId(uuid);
    }
}
