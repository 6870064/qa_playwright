package org.example.responses.update_user_response;

import org.example.responses.update_user_response.Data;

public record UpdateUserResponse(
    boolean success,
    int status,
    String message,
    Data data
) {
}
