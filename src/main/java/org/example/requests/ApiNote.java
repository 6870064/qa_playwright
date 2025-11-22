package org.example.requests;

import org.example.enums.Category;

public record ApiNote(
    String title,
    String description,
    Category category
) {
}
