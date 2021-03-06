package com.cituojt.happyTicketingApi;

import java.util.Arrays;
import java.util.UUID;
import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({@PropertySource("classpath:application.properties"),
        @PropertySource("classpath:auth0.properties")})
@EnableAutoConfiguration
public class HappyTicketingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyTicketingApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepo, ProjectRepository projectRepo) {

        return (args) -> {
            projectRepo.deleteAll();
            userRepo.deleteAll();

            User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");
            User u2 = new User("pofire@example.com", "auth0|123");
            User u3 = new User("some@example.com", "auth0|456");
            User u4 = new User("exuberant@example.com", "auth0|12345");

            Project p = new Project("Watsup", UUID.randomUUID());
            Project p2 = new Project("Cool Whip", UUID.randomUUID());
            p.addMember(u, "OWNER");
            p.addMember(u2, "MEMBER");
            p2.addMember(u2, "OWNER");
            p2.addMember(u, "MEMBER");


            p.addTask("A Really Good Task", u.getEmail(), "TO IMPLEMENT", 2);
            p.addTask("A Really Good Task", u.getEmail(), "TO IMPLEMENT", 2);
            p.addTask("A Really Bad Task", u.getEmail(), "PARTIAL", 3);
            p.addTask("A Really Great Task", u.getEmail(), "COMPLETE", 4);

            projectRepo.saveAll(Arrays.asList(p, p2));
            userRepo.saveAll(Arrays.asList(u, u2, u3, u4));

            Iterable<Project> projects = projectRepo.getProjectsForUser(u.getId());

            for (Project pr : projects) {
                System.out.println("Project Name: " + pr.getName());
                System.out.println("Member Count: " + pr.getMembers().size());
            }
        };

    }

}
