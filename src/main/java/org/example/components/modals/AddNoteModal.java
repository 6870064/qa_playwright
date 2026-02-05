package org.example.components.modals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.enums.NoteCategory;
import org.example.objects.Note;

public class AddNoteModal {
  private final Locator createNoteCategory;
  private final Locator createNoteCompleted;
  private final Locator createNoteTitle;
  private final Locator createNoteDescription;
  private final Locator createNoteSubmit;
  private final Locator cancelCreateNote;

  public AddNoteModal(Page page) {

    this.createNoteCategory = page.getByTestId("note-category");
    this.createNoteCompleted = page.getByTestId("note-completed");
    this.createNoteTitle = page.getByTestId("note-title");
    this.createNoteDescription = page.getByTestId("note-description");
    this.createNoteSubmit = page.getByTestId("note-submit");
    this.cancelCreateNote = page.getByTestId("note-cancel");
  }

  @Step("Selecting category of the note: {category}")
  public void selectCategory(NoteCategory category) {
    createNoteCategory.selectOption(category.name());
  }

  @Step("Complete note: {isCompleted}")
  public void completeNote(boolean isCompleted) {
    if(isCompleted == true) {
      createNoteCompleted.check();
    }
  }

  @Step("Fill title of the note")
  public void fillTitle(String title){
    createNoteTitle.fill(title);
  }

  @Step("Fill description of the note")
  public void fillDescription(String description) {
    createNoteDescription.fill(description);
  }

  @Step("Click 'Create' button")
  public void clickCreateNote() {
    createNoteSubmit.click();
  }

  @Step("Click 'Cancel' button")
  public void cancelCreateNote() {
    cancelCreateNote.click();
  }

  public void createNewNote(Note note) {
    selectCategory(note.getCategory());
    completeNote(note.isCompleted());
    fillTitle(note.getTitle());
    fillDescription(note.getDescription());
    clickCreateNote();

  }
}
