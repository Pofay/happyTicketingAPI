package com.cituojt.happyTicketingApi.modules.userManagement.domain.aggregate;

import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.Email;
import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.UserId;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class User {
    @NonNull
    @Getter
    @EqualsAndHashCode.Include
    private final UserId id;

    @NonNull
    @Getter
    private final Email email;

}
