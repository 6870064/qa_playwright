package org.example.requests;

public record ChangePasswordDto(
    String currentPassword,
    String newPassword
) {

}
