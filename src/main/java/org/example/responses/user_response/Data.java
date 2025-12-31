package org.example.responses.user_response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Data(
    String id,
    String name,
    String email)
 {}
