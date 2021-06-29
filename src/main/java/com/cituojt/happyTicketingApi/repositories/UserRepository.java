package com.cituojt.happyTicketingApi.repositories;

import java.util.Optional;
import com.cituojt.happyTicketingApi.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findByOAuthId(String oAuthId);

    public Optional<User> findByEmail(String email);
}
