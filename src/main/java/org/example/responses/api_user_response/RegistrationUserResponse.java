package org.example.responses.api_user_response;

public record RegistrationUserResponse(
  boolean success,
  int status,
  String message,
  Data data
) {}