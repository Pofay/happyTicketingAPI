package com.cituojt.happyTicketingApi;

// import java.util.Arrays;
// import java.util.List;

// import com.cituojt.happyTicketingApi.entities.Project;
// import com.cituojt.happyTicketingApi.entities.ProjectMember;
// import com.cituojt.happyTicketingApi.entities.ProjectMemberId;
// import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@PropertySources({ @PropertySource("classpath:application.properties"), @PropertySource("classpath:auth0.properties") })
@ComponentScan
public class HappyTicketingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HappyTicketingApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepo, ProjectRepository projectRepo) {

        return (args) -> {
            /*
            projectRepo.deleteAll();
            userRepo.deleteAll();

            User u = new User("pofay@example.com", "auth0|123");
            User u2 = new User("pofire@example.com", "auth0|123");

            Project p = new Project("Watsup");
            Project p2 = new Project("Cool Whip");
            p.addMember(u, "OWNER");
            p2.addMember(u2, "OWNER");

            projectRepo.saveAll(Arrays.asList(p, p2));
            userRepo.saveAll(Arrays.asList(u, u2));

            Iterable<Project> projects = projectRepo.getProjectsForUser(u.getId());

            for (Project pr : projects) {
                System.out.println("Project Name: " + pr.getName());
                System.out.println("Member Count: " + pr.getMembers().size());
            }*/

        };

    }

}
