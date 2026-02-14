package org.example.objects;

import org.example.enums.NoteCategory;

import java.util.Objects;

public class Note {
  NoteCategory category;
  boolean isCompleted;
  String title;
  String description;

  public Note(NoteCategory category, boolean isCompleted, String title, String description) {
    this.category = category;
    this.isCompleted = isCompleted;
    this.title = title;
    this.description = description;
  }

  public NoteCategory getCategory() {
    return category;
  }

  public void setCategory(NoteCategory category) {
    this.category = category;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public void setCompleted(boolean completed) {
    isCompleted = completed;
  }

  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Note note)) return false;
    return isCompleted == note.isCompleted && category == note.category && Objects.equals(title, note.title) && Objects.equals(description, note.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(category, isCompleted, title, description);
  }

  @Override
  public String toString() {
    return "Note{" +
        "category=" + category +
        ", isCompleted=" + isCompleted +
        ", title='" + title + '\'' +
        ", description='" + description + '\'' +
        '}';
  }
}
