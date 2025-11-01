package org.example.responses.api_note_response;

import java.util.List;

public record ApiNotesResponse(
    boolean success,
    int status,
    String message,
    List<Data> data
) {
}
