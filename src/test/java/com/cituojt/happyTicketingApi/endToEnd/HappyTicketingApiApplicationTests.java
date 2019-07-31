package com.cituojt.happyTicketingApi.endToEnd;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:secrets.properties")
public class HappyTicketingApiApplicationTests {

    @Autowired
    private TestRestTemplate template;

    @Value(value = "${auth0.apiAudience}")
    private String audience;

    @Value(value = "${auth0.issuer}")
    private String issuer;

    @Value(value = "${auth0.clientId}")
    private String clientId;

    @Value(value = "${auth0.clientSecret}")
    private String clientSecret;

    private String host;

    @Before
    public void setup() {

        this.host = "http://localhost:8010";

        String body = Auth0RequestBuilder.create().withApiAudience(audience).withClientId(clientId)
                .withClientSecret(clientSecret).withUsernameAndPassword("pofay@example.com", "!pofay123").build();

        HttpResponse<JsonNode> response = Unirest.post(String.format("%soauth/token", issuer))
                .header("content-type", "application/x-www-form-urlencoded").body(body).asJson();
        String accessToken = response.getBody().getObject().getString("access_token");

        template.getRestTemplate().setInterceptors(Collections.singletonList((request, xbody, execution) -> {
            request.getHeaders().add("Authorization", String.format("Bearer %s", accessToken));
            return execution.execute(request, xbody);
        }));
    }

    @After
    public void teardown() {
        Unirest.shutDown();
    }

    @Test
    public void getOnProjectsURLReturns200WhenAuthenticated() {

        HttpStatus actual = this.template.getForEntity(host + "/api/v1/projects", Object.class).getStatusCode();

        assertThat(actual, is(equalTo(HttpStatus.OK)));

    }

}
