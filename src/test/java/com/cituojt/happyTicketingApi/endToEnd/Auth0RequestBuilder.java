package com.cituojt.happyTicketingApi.endToEnd;

import java.util.ArrayList;
import java.util.List;

// NOT IMMUTABLE! USE WITH CARE!
public class Auth0RequestBuilder {

    private String password;
    private String username;
    private String apiAudience;
    private List<String> scopes;
    private String clientSecret;
    private String clientId;

    public static Auth0RequestBuilder create() {
        return new Auth0RequestBuilder();
    }

    private Auth0RequestBuilder() {
        this.username = "";
        this.password = "";
        this.apiAudience = "";
        this.scopes = new ArrayList<>();
        this.clientSecret = "";
        this.clientId = "";
    }

    public Auth0RequestBuilder withUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
        return this;
    }

    public Auth0RequestBuilder addScope(String scope) {
        scopes.add(scope);
        return this;
    }

    public Auth0RequestBuilder withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public Auth0RequestBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public Auth0RequestBuilder withApiAudience(String apiAudience) {
        this.apiAudience = apiAudience;
        return this;
    }

    public String build() {
        String parsedScopes = this.scopes.stream().reduce("", (acc, cur) -> acc + " " + cur);

        return String.format(
                "grant_type=password&username=%s&password=%s&audience=%s&client_id=%s&client_secret=%s&scopes=%s",
                this.username, this.password, this.apiAudience, this.clientId, this.clientSecret, parsedScopes);

    }

}
