package com.cituojt.happyTicketingApi.modules.projectManagement.domain.entities;

import java.time.Duration;

import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketId;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketName;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketStatus;
import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.Email;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Ticket {

    @NonNull
    @Getter
    @EqualsAndHashCode.Include
    private final TicketId id;

    @NonNull
    @Getter
    private TicketName name;

    @NonNull
    @Getter
    private TicketStatus status;

    @NonNull
    @Getter
    private Duration estimatedTime;

    @NonNull
    @Getter
    private Email assignedTo;

}
