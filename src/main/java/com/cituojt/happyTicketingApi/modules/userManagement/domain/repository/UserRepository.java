package com.cituojt.happyTicketingApi.modules.userManagement.domain.repository;

import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.UserId;
import com.cituojt.happyTicketingApi.modules.userManagement.domain.aggregate.User;

import io.vavr.control.Either;

public interface UserRepository {
    public Either<String, User> save(User u);

    public User getById(UserId id);
}
