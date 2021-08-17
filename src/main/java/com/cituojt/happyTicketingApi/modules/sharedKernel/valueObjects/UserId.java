package com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserId {

    @NonNull
    private final UUID value;

    public static UserId fromUUID(UUID id) {
        return new UserId(id);
    }

}
