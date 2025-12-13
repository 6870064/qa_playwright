package org.example.responses.note_response;

public record ApiNoteResponse(
    boolean success,
    int status,
    String message,
    Data data) {}