package com.cituojt.happyTicketingApi.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.UserRepository;
import com.cituojt.happyTicketingApi.requests.users.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

    private UserRepository userRepo;

    @Autowired
    public UsersController(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(value = "/api/v1/users", produces = "application/json")
    public ResponseEntity createUser(@RequestBody CreateUserRequest body, HttpServletRequest req,
            HttpServletResponse res) throws JSONException {
        User u = new User(UUID.randomUUID(), body.getEmail(), body.getUserId());

        userRepo.save(u);

        JSONObject payload = new JSONObject();
        payload.put("message", "User has been saved successfully.");

        return ResponseEntity.status(201).body(payload.toString());
    }
}
