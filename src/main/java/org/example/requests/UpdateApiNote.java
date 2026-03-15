package org.example.requests;

import org.example.enums.NoteCategory;

public record UpdateApiNote(
    String title,
    String description,
    boolean completed,
    NoteCategory category
) {
}
