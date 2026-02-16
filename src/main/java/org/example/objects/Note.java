package org.example.objects;

import org.example.enums.NoteCategory;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a Note.
 * Used to store and compare note data between the UI, API, and test expectations.
 */
public class Note {
  NoteCategory category;
  boolean isCompleted;
  String title;
  String description;

  /**
   * Constructs a new Note with the specified attributes.
   *
   * @param category    the category the note belongs to (e.g., Home, Work)
   * @param isCompleted the status of the note completion
   * @param title       the title of the note
   * @param description the detailed content of the note
   */
  public Note(NoteCategory category, boolean isCompleted, String title, String description) {
    this.category = category;
    this.isCompleted = isCompleted;
    this.title = title;
    this.description = description;
  }

  /**
   * Returns the category of the note.
   *
   * @return the NoteCategory enum value
   */
  public NoteCategory getCategory() {
    return category;
  }

  /**
   * Updates the category of the note.
   *
   * @param category the new NoteCategory to set
   */
  public void setCategory(NoteCategory category) {
    this.category = category;
  }

  /**
   * Returns the description of the note.
   *
   * @return the description string
   */
  public String getDescription() {
    return description;
  }

  /**
   * Updates the description of the note.
   *
   * @param description the new description string
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Returns the title of the note.
   *
   * @return the title string
   */
  public String getTitle() {
    return title;
  }

  /**
   * Updates the title of the note.
   *
   * @param title the new title string
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Returns the completion status of the note.
   *
   * @return true if completed, false otherwise
   */
  public boolean isCompleted() {
    return isCompleted;
  }

  /**
   * Updates the completion status of the note.
   *
   * @param completed the new completion status
   */
  public void setCompleted(boolean completed) {
    isCompleted = completed;
  }

  /**
   * Compares this note to another object for equality.
   * All fields must match for the notes to be considered equal.
   *
   * @param object the reference object with which to compare
   * @return true if this object is the same as the obj argument; false otherwise
   */
  @Override
  public boolean equals(Object object) {
    if (!(object instanceof Note note)) return false;
    return isCompleted == note.isCompleted && category == note.category && Objects.equals(title, note.title) && Objects.equals(description, note.description);
  }

  /**
   * Returns a hash code value for the note.
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(category, isCompleted, title, description);
  }

  /**
   * Returns a string representation of the note.
   *
   * @return a string describing the note's attributes
   */
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