package org.example.objects;

import org.example.enums.NoteCategory;

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
}
