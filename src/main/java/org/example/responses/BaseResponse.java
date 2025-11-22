package org.example.responses;

public record BaseResponse(
    boolean success,
    int status,
    String message
) {
}
