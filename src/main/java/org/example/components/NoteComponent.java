package org.example.components;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import org.example.components.modals.DeleteNoteModal;
import org.example.components.modals.NoteModal;

/**
 * Component representing a single note card on the home page.
 * Provides methods to interact with and retrieve data from a specific note.
 */
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

  /**
   * Opens the detailed view of the note by clicking the view button.
   */
  @Step("Click 'View' button on the note card")
  public void viewNote() {
    viewButton.click();
  }

  /**
   * Opens the edit modal for the note and returns the modal object.
   *
   * @return a new instance of NoteModal
   */
  @Step("Click 'Edit' button on the note card")
  public NoteModal editNote() {
    editButton.click();
    return new NoteModal(root.page());
  }

  /**
   * Opens the delete confirmation modal for the note and returns the modal object.
   *
   * @return a new instance of DeleteNoteModal
   */
  @Step("Open delete note modal")
  public DeleteNoteModal deleteNote() {
    deleteButton.click();
    return new DeleteNoteModal(root.page());
  }

  /**
   * Retrieves the title text displayed on the note card.
   *
   * @return the trimmed title string
   */
  public String getTitle() {
    return title.innerText().trim();
  }

  /**
   * Retrieves the description text displayed on the note card.
   *
   * @return the trimmed description string
   */
  public String getDescription() {
    return description.innerText().trim();
  }

  /**
   * Toggles the note's completion switch to the checked state.
   */
  @Step("Toggle note completion switch")
  public void completeNote() {
    isCompletedToggle.check();
  }

  public boolean isNoteCompleted() {
    return isCompletedToggle.isChecked();
  }
}
