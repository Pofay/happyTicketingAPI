package com.cituojt.happyTicketingApi.modules.projectManagement.domain.dtos;

import java.time.Duration;

import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.ProjectId;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketId;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketName;
import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.TicketStatus;
import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.UserId;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class AddTicketToProjectDTO {

    @Getter
    private ProjectId projectId;
    @Getter
    private UserId userId;
    @Getter
    private TicketId ticketId;
    @Getter
    private TicketName ticketName;
    @Getter
    private TicketStatus ticketStatus;
    @Getter
    private Duration estimatedTime;

}
