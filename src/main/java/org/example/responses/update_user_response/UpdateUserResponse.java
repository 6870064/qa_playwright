package org.example.responses.update_user_response;

public record UpdateUserResponse(
    boolean success,
    int status,
    String message,
    Data data
) {
}
