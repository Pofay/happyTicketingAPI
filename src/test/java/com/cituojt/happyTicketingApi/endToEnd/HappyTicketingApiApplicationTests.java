package com.cituojt.happyTicketingApi.endToEnd;

import com.cituojt.happyTicketingApi.entities.Project;
import com.cituojt.happyTicketingApi.entities.Task;
import com.cituojt.happyTicketingApi.entities.User;
import com.cituojt.happyTicketingApi.repositories.ProjectRepository;
import com.cituojt.happyTicketingApi.repositories.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
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
import java.util.UUID;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@Import(MockConfiguration.class)
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
    public void setup_authentication_and_context() {
        userRepo.deleteAll();
        projectRepo.deleteAll();

        this.bearerToken = this.requestBearerTokenFromAuth0();

        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @After
    public void delete_all_records_and_close_event_loop() {
        userRepo.deleteAll();
        projectRepo.deleteAll();
        Unirest.shutDown();
    }

    @Test
    public void authenticated_user_with_user_creds_is_allowed_access_to_projects()
            throws Exception {
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        userRepo.save(u);

        mvc.perform(get("/api/v1/projects").header("Authorization", this.bearerToken))
                .andExpect(status().isOk());
    }

    @Test
    public void authenticated_user_with_no_user_creds_access_not_allowed() throws Exception {
        String errorMessage = "access_token user is not yet registered or doesn't exist.";

        mvc.perform(get("/api/v1/projects").header("Authorization", this.bearerToken))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.error", is(equalTo(errorMessage))));
    }

    @Test
    public void user_gets_only_projects_where_he_is_member_regardless_of_role() throws Exception {
        projectRepo.deleteAll();
        Project p1 = new Project("Customer Satisfaction", UUID.randomUUID());
        Project p2 = new Project("Scrabble Trainer", UUID.randomUUID());
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        p1.addMember(u, "OWNER");
        p2.addMember(u, "OWNER");

        projectRepo.saveAll(Arrays.asList(p1, p2));
        userRepo.save(u);

        mvc.perform(get("/api/v1/projects").header("Authorization", this.bearerToken))
                .andDo(print())
                .andExpect(jsonPath("$.data[:1].name", hasItem("Customer-Satisfaction")))
                .andExpect(jsonPath("$.data[:1].members", iterableWithSize(1)))
                .andExpect(jsonPath("$.data[:1].tasks", is(notNullValue())))
                .andExpect(jsonPath("$.data[:1].channelName", hasItem(p1.getChannelName())))
                .andExpect(jsonPath("$.data[:2].name", hasItem("Scrabble-Trainer")));
    }

    @Test
    public void created_projects_contains_members_tasks_name_and_channelName() throws Exception {
        UUID channelId = UUID.randomUUID();
        String projectName = "Hotel Management";
        Project p = new Project(projectName, channelId);
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        p.addMember(u, "OWNER");

        projectRepo.save(p);
        userRepo.save(u);

        int expectedId = Integer.parseInt(p.getId().toString());
        String channelName = String.format("%s@%s", channelId, projectName.replace(' ', '-'));

        mvc.perform(get("/api/v1/projects/" + p.getId()).header("Authorization", this.bearerToken))
                .andExpect(jsonPath("$.name", equalTo(p.getName())))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.tasks", iterableWithSize(0)))
                .andExpect(jsonPath("$.members", iterableWithSize(1)))
                .andExpect(jsonPath("$.channelName", equalTo(channelName)))
                .andExpect(jsonPath("$.members[:1].email", hasItem(u.getEmail())));
    }

    @Test
    public void can_create_a_project_through_post() throws Exception {
        String projectName = "ProjectM";
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");

        userRepo.save(u);

        int userId = Integer.parseInt(u.getId().toString());

        JSONObject payload = new JSONObject();
        payload.put("name", projectName);

        mvc.perform(post("/api/v1/projects/").header("Authorization", this.bearerToken)
                .contentType(MediaType.APPLICATION_JSON).content(payload.toString()))
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(projectName)))
                .andExpect(jsonPath("$.tasks", iterableWithSize(0)))
                .andExpect(jsonPath("$.members", iterableWithSize(1)))
                .andExpect(jsonPath("$.channelName", is(notNullValue())))
                .andExpect(jsonPath("$.members[:1].email", hasItem(u.getEmail())))
                .andExpect(jsonPath("$.members[:1].id", hasItem(userId)));
    }

    @Test
    public void creating_a_project_requires_name() throws Exception {
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
    public void adding_task_is_through_posting_to_specific_project() throws Exception {
        Project p = new Project("Hotel Management", UUID.randomUUID());
        User u = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");
        p.addMember(u, "OWNER");
        projectRepo.save(p);
        userRepo.save(u);

        String taskName = "MakeDB";
        String status = "PARTIAL";
        Integer estimatedTime = 2;

        JSONObject payload = new JSONObject();
        payload.put("name", taskName);
        payload.put("status", status);
        payload.put("estimatedTime", estimatedTime);

        mvc.perform(post("/api/v1/projects/" + p.getId() + "/tasks")
                .header("Authorization", this.bearerToken).contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString())).andExpect(status().isCreated())
                .andExpect(jsonPath("$.tasks[:1].id", is(notNullValue())))
                .andExpect(jsonPath("$.tasks[:1].name", hasItem(taskName)))
                .andExpect(jsonPath("$.tasks[:1].status", hasItem(status)))
                .andExpect(jsonPath("$.tasks[:1].estimatedTime", hasItem(estimatedTime)))
                .andExpect(jsonPath("$.tasks[:1].projectId", hasItem(p.getId().intValue())))
                .andExpect(jsonPath("$.tasks[:1].assignedTo", hasItem(u.getEmail())));
    }

    @Test
    public void registered_emails_can_be_added_to_project_members() throws Exception {
        Project p = new Project("Hotel Management", UUID.randomUUID());
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
    public void nonregistered_emails_are_not_allowed() throws Exception {
        Project p = new Project("Hotel Management", UUID.randomUUID());
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
    public void updating_a_task_requires_all_fields() throws Exception {
        Project p = new Project("Hotel Management", UUID.randomUUID());
        User u1 = new User("pofay@example.com", "auth0|5d4185285fa52d0cfa094cc1");
        User u2 = new User("pofire@example.com", "auth0|1234");
        Task t = new Task(UUID.randomUUID(), "Some Task", u1.getEmail(), "TO IMPLEMENT", 4);
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
        payload.put("estimatedTime", 3);

        String id = t.getId();

        mvc.perform(put("/api/v1/projects/" + p.getId() + "/tasks")
                .header("Authorization", this.bearerToken).contentType(MediaType.APPLICATION_JSON)
                .content(payload.toString())).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id))).andExpect(jsonPath("$.name", is(t.getName())))
                .andExpect(jsonPath("$.status", is(t.getStatus())))
                .andExpect(jsonPath("$.estimatedTime", is(3)))
                .andExpect(jsonPath("$.assignedTo", is(u2.getEmail())));
    }

    public String requestBearerTokenFromAuth0() {
        String body = Auth0RequestBuilder.create().withApiAudience(audience).withClientId(clientId)
                .withClientSecret(clientSecret)
                .withUsernameAndPassword("pofay@example.com", "!pofay123").build();


        HttpResponse<JsonNode> response = Unirest.post(String.format("%soauth/token", issuer))
                .header("content-type", "application/x-www-form-urlencoded").body(body).asJson();

        return String.format("Bearer %s", response.getBody().getObject().getString("access_token"));
    }
}
