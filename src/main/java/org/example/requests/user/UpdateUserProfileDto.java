package org.example.requests.user;

public record UpdateUserProfileDto(
    String name,
    String phone,
    String company) {
}
