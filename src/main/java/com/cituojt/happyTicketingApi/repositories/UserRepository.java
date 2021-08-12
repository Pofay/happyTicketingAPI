package com.cituojt.happyTicketingApi.repositories;

import java.util.Optional;
import com.cituojt.happyTicketingApi.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

    public Optional<UserEntity> findByOauthId(String oAuthId);

    public Optional<UserEntity> findByEmail(String email);
}
