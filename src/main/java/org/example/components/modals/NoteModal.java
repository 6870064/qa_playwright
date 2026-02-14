package org.example.components.modals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.components.NoteComponent;
import org.example.enums.NoteCategory;
import org.example.objects.Note;

public class NoteModal {
  private final Locator noteCategory;
  private final Locator isNoteCompleted;
  private final Locator noteTitle;
  private final Locator noteDescription;
  private final Locator noteSubmit;
  private final Locator cancelCreateNote;

  public NoteModal(Page page) {
    this.noteCategory = page.getByTestId("note-category");
    this.isNoteCompleted = page.getByTestId("note-completed");
    this.noteTitle = page.getByTestId("note-title");
    this.noteDescription = page.getByTestId("note-description");
    this.noteSubmit = page.getByTestId("note-submit");
    this.cancelCreateNote = page.getByTestId("note-cancel");
  }

  @Step("Selecting category of the note: {category}")
  public void selectCategory(NoteCategory category) {
    noteCategory.selectOption(category.name());
  }

  @Step("Complete note: {isCompleted}")
  public void completeNote(boolean isCompleted) {
    if(isCompleted == true) {
      isNoteCompleted.check();
    }
  }

  @Step("Fill title of the note")
  public void fillTitle(String title){
    noteTitle.fill(title);
  }

  @Step("Fill description of the note")
  public void fillDescription(String description) {
    noteDescription.fill(description);
  }

  @Step("Click 'Create' button")
  public void clickCreateNote() {
    noteSubmit.click();
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

  public String getTitle() {
    return noteTitle.inputValue().trim();
  }

  public String getDescription() {
    return noteDescription.inputValue().trim();
  }

  public boolean isCompleted() {
    return isNoteCompleted.isChecked();
  }

  public NoteCategory getCategory() {
    return NoteCategory.valueOf(noteCategory.inputValue());
  }

  public void compareNote(Note expectedNote) {
    Note actualNote = new Note(
        getCategory(),
        isCompleted(),
        getTitle(),
        getDescription());

   if(!actualNote.equals(expectedNote)) {
     throw new IllegalStateException(
         String.format("Content of the note is not equal:" +
             "Expect: %s." +
             "Received: %s", expectedNote, actualNote));
   }
  }

}
