package org.example.responses.api_note_response;

public record ApiNoteResponse(
    boolean success,
    int status,
    String message,
    Data data) {}