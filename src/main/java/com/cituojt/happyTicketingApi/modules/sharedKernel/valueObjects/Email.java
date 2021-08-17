package com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Email {

    @NonNull
    private final String value;

    public static Email of(String email) {
        return new Email(email);
    }

}
