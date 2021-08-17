package com.cituojt.happyTicketingApi.modules.projectManagement.domain.entities;

import com.cituojt.happyTicketingApi.modules.projectManagement.domain.valueObjects.MemberRole;
import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.Email;
import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.UserId;
import com.cituojt.happyTicketingApi.modules.userManagement.domain.aggregate.User;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProjectMember {

    @NonNull
    @Getter
    @EqualsAndHashCode.Include
    private final UserId userId;

    @NonNull
    @Getter
    private final Email email;

    @NonNull
    @Getter
    private final MemberRole role;

    public static ProjectMember asOwner(User user) {
        return new ProjectMember(user.getId(), user.getEmail(), MemberRole.OWNER);
    }

    public static ProjectMember asMember(User user) {
        return new ProjectMember(user.getId(), user.getEmail(), MemberRole.MEMBER);
    }

}
