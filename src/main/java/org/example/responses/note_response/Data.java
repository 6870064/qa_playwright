package org.example.responses.note_response;

public record Data(
    String id,
    String title,
    String description,
    String category,
    boolean completed,
    String created_at,
    String updated_at,
    String user_id) {}
