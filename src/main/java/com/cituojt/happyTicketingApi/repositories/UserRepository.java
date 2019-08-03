package com.cituojt.happyTicketingApi.repositories;

import com.cituojt.happyTicketingApi.entities.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    public User findByOAuthId(String oAuthId);

}
