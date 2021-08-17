package com.cituojt.happyTicketingApi.unit;

import java.util.HashMap;
import java.util.Map;

import com.cituojt.happyTicketingApi.modules.sharedKernel.valueObjects.UserId;
import com.cituojt.happyTicketingApi.modules.userManagement.domain.aggregate.User;
import com.cituojt.happyTicketingApi.modules.userManagement.domain.repository.UserRepository;

import io.vavr.control.Either;

public class InMemoryUserRepo implements UserRepository {

    private Map<UserId, User> users;

    public InMemoryUserRepo() {
        this.users = new HashMap<>();
    }

    public Either<String, User> save(User user) {
        this.users.put(user.getId(), user);
        return Either.right(user);
    }

    public User getById(UserId userId) {
        return users.get(userId);
    }

}
