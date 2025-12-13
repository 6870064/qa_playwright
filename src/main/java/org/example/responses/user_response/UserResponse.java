package org.example.responses.user_response;

public record UserResponse(
  boolean success,
  int status,
  String message,
  Data data
) {}