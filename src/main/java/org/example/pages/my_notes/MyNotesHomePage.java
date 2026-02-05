package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.components.HeaderComponent;
import org.example.constants.routes.UIRotes;
import org.example.enums.NoteCategory;
import org.example.objects.Note;
import org.example.pages.BasePage;

public class MyNotesHomePage extends BasePage {
  public final HeaderComponent header;

  private final Locator searchButton = page.getByTestId("search-btn");
  private final Locator searchInput = page.getByTestId("search-btn");
  private final Locator addNoteButton = page.getByTestId("add-new-note");
  private final Locator noNotes = page.getByTestId("no-notes-message");
  private final Locator categoryAll = page.getByTestId("category-all");
  private final Locator categoryHome = page.getByTestId("category-home");
  private final Locator categoryWork = page.getByTestId("category-work");
  private final Locator categoryPersonal = page.getByTestId("category-personal");
  private final Locator createNoteCategory = page.getByTestId("note-category");
  private final Locator createNoteCompleted = page.getByTestId("note-completed");
  private final Locator createNoteTitle = page.getByTestId("note-title");
  private final Locator createNoteDescription = page.getByTestId("note-description");
  private final Locator createNoteSubmit = page.getByTestId("note-submit");
  private final Locator cancelCreateNote = page.getByTestId("note-cancel");
  private final Locator viewNote = page.getByTestId("note-view");
  private final Locator editNote = page.getByTestId("note-edit");
  private final Locator deleteNote = page.getByTestId("note-delete");

  public MyNotesHomePage(Page page) {
    super(page);
    this.header = new HeaderComponent(page);
  }

  @Override
  protected String path() {
    return UIRotes.HOME;
  }

  @Step("Click '+Add Note' button")
  public void clickAddNote() {
    addNoteButton.click();
  }

  public void selectCategory(NoteCategory category) {
    createNoteCategory.selectOption(category.name());
  }

  @Step("Complete note")
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
    completeNote(note.isCompleted());
    fillTitle(note.getTitle());
    fillDescription(note.getDescription());
    clickCreateNote();

  }
}
