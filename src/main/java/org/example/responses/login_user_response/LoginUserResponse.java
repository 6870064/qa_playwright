package org.example.responses.login_user_response;


public record LoginUserResponse(
  boolean success,
  int status,
  String message,
  Data data
) {}