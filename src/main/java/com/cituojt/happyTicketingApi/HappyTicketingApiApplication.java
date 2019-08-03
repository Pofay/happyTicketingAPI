package com.cituojt.happyTicketingApi;

import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.ProjectMember;
import com.cituojt.happyTicketingApi.entities.ProjectMemberId;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({ @PropertySource("classpath:application.properties"), @PropertySource("classpath:auth0.properties") })
public class HappyTicketingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyTicketingApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepo, ProjectRepository projectRepo) {

        return (args) -> {
            projectRepo.deleteAll();
            userRepo.deleteAll();

            User u = new User("pofay@example.com", "auth0|123");

            Project p = new Project("Watsup");
            p.addMember(u, "OWNER");

            projectRepo.save(p);
            userRepo.save(u);

            Iterable<Project> projects = projectRepo.findAll();
            for (Project pr : projects) {
                System.out.println(String.format("%s, %d", pr.getName(), pr.getId()));
                for (ProjectMember member : pr.getMembers()) {
                    User us = member.getUser();
                    System.out.println(String.format("%s, %s, %s", us.getEmail(), us.getOAuthId(), member.getRole()));
                }
            }
        };

    }

}
