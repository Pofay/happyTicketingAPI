package com.cituojt.happyTicketingApi.endToEnd;

import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.Task;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import net.minidev.json.JSONObject;
import java.util.Arrays;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Import(TestConfig.class)
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
    public void getOnProjects_shouldReturnAnErrorWhenUserIsNotFound() throws Exception {
        String errorMessage = "access_token user is not yet registered or doesn't exist.";

        mvc.perform(get("/api/v1/projects").header("Authorization", this.bearerToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error", is(equalTo(errorMessage))));
    }

    @Test
    public void withSavedProject_getReturnsCorrectResult() throws Exception {
        Project p = new Project("ProjectM");
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        p.addMember(u, "OWNER");

        projectRepo.save(p);
        userRepo.save(u);

        mvc.perform(get("/api/v1/projects").header("Authorization", this.bearerToken))
                .andExpect(jsonPath("$.data[:1].name", hasItem("ProjectM")))
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

        mvc.perform(get("/api/v1/projects/" + p.getId()).header("Authorization", this.bearerToken))
                .andExpect(status().isOk()).andExpect(jsonPath("$.name", equalTo(p.getName())))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.members[:1].email", hasItem(u.getEmail())));
    }

    @Test
    public void postWithBodyReturnsCorrectResult() throws Exception {
        String projectName = "ProjectM";
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        userRepo.save(u);

        int userId = Integer.parseInt(u.getId().toString());

        JSONObject payload = new JSONObject();
        payload.put("name", projectName);

        mvc.perform(post("/api/v1/projects/").header("Authorization", this.bearerToken)
                .contentType(MediaType.APPLICATION_JSON).content(payload.toString()))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.name", is(projectName)))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.members[:1].email", hasItem(u.getEmail())))
                .andExpect(jsonPath("$.members[:1].id", hasItem(userId)));
    }

    @Test
    public void postWithEmptyNameReturns400WithErrorMessage() throws Exception {
        String projectName = "";
        String errorMessage = "name cannot be empty";

        JSONObject payload = new JSONObject();
        payload.put("name", projectName);

        mvc.perform(post("/api/v1/projects/").header("Authorization", this.bearerToken)
                .contentType(MediaType.APPLICATION_JSON).content(payload.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is(errorMessage)));
    }

    @Test
    public void postToTaskForProjectReturns201() throws Exception {
        Project p = new Project("Hotel Management");
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");
        p.addMember(u, "OWNER");
        projectRepo.save(p);
        userRepo.save(u);

        String taskName = "MakeDB";
        String status = "PARTIAL";

        JSONObject payload = new JSONObject();
        payload.put("name", taskName);
        payload.put("status", status);

        mvc.perform(post("/api/v1/projects/" + p.getId() + "/tasks")
                .header("Authorization", this.bearerToken).contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString())).andExpect(status().isCreated())
                .andExpect(jsonPath("$.tasks[:1].id", is(notNullValue())))
                .andExpect(jsonPath("$.tasks[:1].name", hasItem(taskName)))
                .andExpect(jsonPath("$.tasks[:1].status", hasItem(status)))
                .andExpect(jsonPath("$.tasks[:1].assignedTo", hasItem(u.getEmail())));
    }

    @Test
    public void withFoundEmail_addingGroupMembersToProjectReturns201WithMembersFieldChanged()
            throws Exception {
        Project p = new Project("Hotel Management");
        User u1 = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");
        User u2 = new User("pofire@example.com", "auth0|123456");
        p.addMember(u1, "OWNER");
        projectRepo.save(p);
        userRepo.saveAll(Arrays.asList(u1, u2));

        JSONObject payload = new JSONObject();
        payload.put("memberEmail", u2.getEmail());

        mvc.perform(post("/api/v1/projects/" + p.getId() + "/members")
                .header("Authorization", this.bearerToken).contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString())).andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.members[:2].email", hasItem(u2.getEmail())));
    }

    @Test
    public void withNoMatchingEmail_addingMembersToProjectReturns403WithErrorMessage()
            throws Exception {
        Project p = new Project("Hotel Management");
        User u1 = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");
        p.addMember(u1, "OWNER");
        projectRepo.save(p);
        userRepo.save(u1);

        JSONObject payload = new JSONObject();
        payload.put("memberEmail", "notExisting@example.com");

        mvc.perform(post("/api/v1/projects/" + p.getId() + "/members")
                .header("Authorization", this.bearerToken).contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString())).andExpect(status().isForbidden()).andExpect(
                        jsonPath("$.error", is(equalTo("email is not yet registered to system."))));
    }

    @Test
    public void updateTaskFieldsReturnsUpdatedValue() throws Exception {
        Project p = new Project("Hotel Management");
        User u1 = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");
        User u2 = new User("pofire@example.com", "auth0|1234");
        Task t = new Task("Some Task", u1.getEmail(), "TO IMPLEMENT");
        p.addMember(u1, "OWNER");
        p.addMember(u2, "MEMBER");
        p.addTask(t);
        projectRepo.save(p);
        userRepo.saveAll(Arrays.asList(u1, u2));

        JSONObject payload = new JSONObject();
        payload.put("assignedTo", u2.getEmail());
        payload.put("status", "TO IMPLEMENT");
        payload.put("name", t.getName());
        payload.put("id", t.getId());

        int id = Integer.parseInt(t.getId().toString());

        mvc.perform(put("/api/v1/projects/" + p.getId() + "/tasks")
                .header("Authorization", this.bearerToken).contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString())).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id))).andExpect(jsonPath("$.name", is(t.getName())))
                .andExpect(jsonPath("$.status", is(t.getStatus())))
                .andExpect(jsonPath("$.assignedTo", is(u2.getEmail())));
    }

}
