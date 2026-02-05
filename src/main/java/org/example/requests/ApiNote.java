package org.example.requests;

import org.example.enums.NoteCategory;

public record ApiNote(
    String title,
    String description,
    NoteCategory category
) {
}
