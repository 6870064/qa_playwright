package org.example.responses.login_user_response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(
    String id,
    String name,
    String email,
    String token)
 {}
