package com.cituojt.happyTicketingApi.endToEnd;

import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:secrets.properties")
public class HappyTicketingApiApplicationTests {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserRepository userRepo;

    @Value(value = "${auth0.apiAudience}")
    private String audience;

    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Value(value = "${auth0.clientId}")
    private String clientId;

    @Value(value = "${auth0.clientSecret}")
    private String clientSecret;

    private MockMvc mvc;

    private String accessToken;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        String body = Auth0RequestBuilder.create().withApiAudience(audience).withClientId(clientId)
                .withClientSecret(clientSecret).withUsernameAndPassword("pofay@example.com", "!pofay123").build();

        HttpResponse<JsonNode> response = Unirest.post(String.format("%soauth/token", issuer))
                .header("content-type", "application/x-www-form-urlencoded").body(body).asJson();
        this.accessToken = response.getBody().getObject().getString("access_token");

        mvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }

    @AfterEach
    public void tearDown() {
        userRepo.deleteAll();
        projectRepo.deleteAll();
    }

    @After
    public void teardown() {
        Unirest.shutDown();
    }

    @Test
    public void getOnProjectsURLReturns200WhenAuthenticated() throws Exception {
        mvc.perform(get("/api/v1/projects").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk());
    }

    @Test
    public void withSavedProject_getReturnsCorrectResult() throws Exception {
        // Create JPA Model (Project, User)
        Project p = new Project("ProjectM");
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        // Connect User to Project
        p.addMember(u, "OWNER");

        // Save to repository
        projectRepo.save(p);
        userRepo.save(u);

        mvc.perform(get("/api/v1/projects").header("Authorization", "Bearer " + accessToken))
        .andDo(print())
                .andExpect(jsonPath("$.data[:1].name", hasItem("ProjectM")))
                .andExpect(jsonPath("$.data[:1].url", hasItem("/v1/projects/1")));
    }

}
