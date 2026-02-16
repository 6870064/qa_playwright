package org.example.components.modals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.enums.NoteCategory;
import org.example.objects.Note;

public class NoteModal {
  private final Locator noteCategory;
  private final Locator isNoteCompleted;
  private final Locator noteTitle;
  private final Locator noteDescription;
  private final Locator noteSubmit;
  private final Locator cancelCreateNote;

  /**
   * Page object representing the modal for creating and editing notes.
   */
  public NoteModal(Page page) {
    this.noteCategory = page.getByTestId("note-category");
    this.isNoteCompleted = page.getByTestId("note-completed");
    this.noteTitle = page.getByTestId("note-title");
    this.noteDescription = page.getByTestId("note-description");
    this.noteSubmit = page.getByTestId("note-submit");
    this.cancelCreateNote = page.getByTestId("note-cancel");
  }

  /**
   * Selects the specified category from the category dropdown.
   *
   * @param category the category to be selected
   */
  @Step("Selecting category of the note: {category}")
  public void selectCategory(NoteCategory category) {
    noteCategory.selectOption(category.name());
  }

  /**
   * Sets the completion status of the note.
   *
   * @param isCompleted true to check the checkbox, false to uncheck
   */
  @Step("Set note completion status to: {isCompleted}")
  public void completeNote(boolean isCompleted) {
    if (isCompleted) {
      isNoteCompleted.check();
    }
  }

  /**
   * Fills the title input field with the provided text.
   *
   * @param title the title of the note
   */
  @Step("Fill title of the note")
  public void fillTitle(String title) {
    noteTitle.fill(title);
  }

  /**
   * Update the title input field with the provided text.
   *
   * @param title the title of the note
   */
  @Step("Update title of the note")
  public void updateTitle(String title) {
    noteTitle.clear();
    noteTitle.fill(title);
  }

  /**
   * Fills the description textarea with the provided text.
   *
   * @param description the description of the note
   */
  @Step("Fill description of the note")
  public void fillDescription(String description) {
    noteDescription.fill(description);
  }

  /**
   * Updated the description textarea with the provided text.
   *
   * @param description the description of the note
   */
  @Step("Updated description of the note")
  public void updateDescription(String description) {
    noteDescription.clear();
    noteDescription.fill(description);
  }

  /**
   * Submits the note form by clicking the create/submit button.
   */
  @Step("Click 'Create/Save' button")
  public void clickSubmitNote() {
    noteSubmit.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    noteSubmit.click();
  }

  /**
   * Closes the modal without saving changes.
   */
  @Step("Click 'Cancel' button")
  public void cancelCreateNote() {
    cancelCreateNote.click();
  }

  /**
   * Closes the modal without saving changes.
   */
  @Step("Click 'Cancel' button without editing the note")
  public void cancelEditNote() {
    cancelCreateNote.click();
  }

  /**
   * Performs a full note creation or update flow using a Note object.
   *
   * @param note the note data object containing all fields
   */
  public void createNewNote(Note note) {
    selectCategory(note.getCategory());
    completeNote(note.isCompleted());
    fillTitle(note.getTitle());
    fillDescription(note.getDescription());
    clickSubmitNote();
  }


  public void updateNote(Note note) {
    selectCategory(note.getCategory());
    completeNote(note.isCompleted());
    updateTitle(note.getTitle());
    updateDescription(note.getDescription());
    clickSubmitNote();
  }

  /**
   * Retrieves the current value of the title input.
   *
   * @return the trimmed title string
   */
  public String getTitle() {
    return noteTitle.inputValue().trim();
  }

  /**
   * Retrieves the current value of the description textarea.
   *
   * @return the trimmed description string
   */
  public String getDescription() {
    return noteDescription.inputValue().trim();
  }

  /**
   * Checks if the note completion checkbox is currently selected.
   *
   * @return true if checked, false otherwise
   */
  public boolean isCompleted() {
    return isNoteCompleted.isChecked();
  }

  /**
   * Retrieves the currently selected category from the dropdown.
   *
   * @return the NoteCategory enum constant
   */
  public NoteCategory getCategory() {
    return NoteCategory.valueOf(noteCategory.inputValue());
  }

  /**
   * Compares the current modal data with an expected Note object.
   * Throws an exception if data does not match.
   *
   * @param expectedNote the note data to compare against
   * @throws IllegalStateException if actual data differs from expected
   */
  public void compareNote(Note expectedNote) {
    Note actualNote = new Note(
        getCategory(),
        isCompleted(),
        getTitle(),
        getDescription());

    if (!actualNote.equals(expectedNote)) {
      throw new IllegalStateException(
          String.format("Content of the note is not equal:" +
              "Expect: %s." +
              "Received: %s", expectedNote, actualNote));
    }
  }
}
