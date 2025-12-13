package org.example.responses.note_response;

import java.util.List;

public record ApiNotesResponse(
    boolean success,
    int status,
    String message,
    List<Data> data
) {
}
