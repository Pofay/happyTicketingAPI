package com.cituojt.happyTicketingApi.endToEnd;

import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import java.util.Arrays;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
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

    private String bearerToken;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        userRepo.deleteAll();
        projectRepo.deleteAll();

        String body = Auth0RequestBuilder.create().withApiAudience(audience).withClientId(clientId)
                .withClientSecret(clientSecret)
                .withUsernameAndPassword("pofay@example.com", "!pofay123").build();

        HttpResponse<JsonNode> response = Unirest.post(String.format("%soauth/token", issuer))
                .header("content-type", "application/x-www-form-urlencoded").body(body).asJson();
        this.bearerToken = String.format("Bearer %s",
                response.getBody().getObject().getString("access_token"));

        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @After
    public void teardown() {
        Unirest.shutDown();
        userRepo.deleteAll();
        projectRepo.deleteAll();
    }

    @Test
    public void getOnProjectsURLReturns200WhenAuthenticated() throws Exception {
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        userRepo.save(u);

        mvc.perform(get("/api/v1/projects").header("Authorization", this.bearerToken))
                .andExpect(status().isOk());
    }

    @Test
    public void withSavedProject_getReturnsCorrectResult() throws Exception {
        Project p = new Project("ProjectM");
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        p.addMember(u, "OWNER");

        projectRepo.save(p);
        userRepo.save(u);

        mvc.perform(get("/api/v1/projects").header("Authorization", this.bearerToken))
                .andDo(print()).andExpect(jsonPath("$.data[:1].name", hasItem("ProjectM")))
                .andExpect(jsonPath("$.data[:1].url", hasItem("/v1/projects/" + p.getId())));
    }

    @Test
    public void triangulateOnGet() throws Exception {
        Project p1 = new Project("Customer Satisfaction");
        Project p2 = new Project("Scrabble Trainer");
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        p1.addMember(u, "OWNER");
        p2.addMember(u, "OWNER");

        projectRepo.saveAll(Arrays.asList(p1, p2));
        userRepo.save(u);

        mvc.perform(get("/api/v1/projects").header("Authorization", this.bearerToken))
                .andDo(print())
                .andExpect(jsonPath("$.data[:1].name", hasItem("Customer Satisfaction")))
                .andExpect(jsonPath("$.data[:1].url", hasItem("/v1/projects/" + p1.getId())))
                .andExpect(jsonPath("$.data[:2].name", hasItem("Scrabble Trainer")))
                .andExpect(jsonPath("$.data[:2].url", hasItem("/v1/projects/" + p2.getId())));
    }

    @Test
    public void getForConnectedProjectReturnsCorrectResult() throws Exception {
        Project p = new Project("Hotel Management");
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        p.addMember(u, "OWNER");

        projectRepo.save(p);
        userRepo.save(u);

        int expectedId = Integer.parseInt(p.getId().toString());

        mvc.perform(get("/api/v1/projects/" + p.getId()).header("Authorization",
                this.bearerToken)).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(p.getName())))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.members[:1].email", hasItem(u.getEmail())));
    }

    @Test
    public void getForUnconnectedProjectReturns403WithReason() {

    }



}
