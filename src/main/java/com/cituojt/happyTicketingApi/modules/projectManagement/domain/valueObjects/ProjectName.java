package com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ProjectName {

    @NonNull
    private final String value;

    public static ProjectName of(String name) {
        return new ProjectName(name);
    }
}
