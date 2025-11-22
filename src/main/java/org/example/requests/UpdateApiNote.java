package org.example.requests;

import org.example.enums.Category;

public record UpdateApiNote(
    String title,
    String description,
    boolean completed,
    Category category
) {
}
