package org.example.components;

import com.microsoft.playwright.Locator;
import org.example.components.modals.DeleteNoteModal;
import org.example.components.modals.NoteModal;

public class NoteComponent {
  private final Locator root;
  private final Locator title;
  private final Locator description;
  private final Locator viewButton;
  private final Locator editButton;
  private final Locator deleteButton;
  private final Locator isCompletedToggle;

  public NoteComponent(Locator root) {
    this.root = root;
    this.title = root.getByTestId("note-card-title");
    this.description = root.getByTestId("note-card-description");
    this.viewButton = root.getByTestId("note-view");
    this.deleteButton = root.getByTestId("note-delete");
    this.editButton = root.getByTestId("note-edit");
    this.isCompletedToggle = root.getByTestId("toggle-note-switch");
  }

  public void viewNote() {
    viewButton.click();
  }

  public NoteModal editNote() {
    editButton.click();
    return new NoteModal(root.page());
  }

  public DeleteNoteModal deleteNote() {
    deleteButton.click();
    return new DeleteNoteModal(root.page());
  }

  public String getTitle() {
    return title.innerText().trim();
  }

  public String getDescription() {
    return description.innerText().trim();
  }

  public void completeNote() {
    isCompletedToggle.check();
  }

  public boolean isNoteCompleted() {
    return isCompletedToggle.isChecked();
  }


}
