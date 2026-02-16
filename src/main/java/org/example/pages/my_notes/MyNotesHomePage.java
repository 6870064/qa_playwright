package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.components.HeaderComponent;
import org.example.components.NoteComponent;
import org.example.components.modals.NoteModal;
import org.example.constants.routes.UIRotes;
import org.example.objects.Note;
import org.example.pages.BasePage;

public class MyNotesHomePage extends BasePage {
  public final HeaderComponent header;

  private final Locator noteRoot = page.getByTestId("note-card");
  private final Locator searchButton = page.getByTestId("search-btn");
  private final Locator searchInput = page.getByTestId("search-btn");
  private final Locator addNoteButton = page.getByTestId("add-new-note");
  private final Locator noNotes = page.getByTestId("no-notes-message");
  private final Locator categoryAll = page.getByTestId("category-all");
  private final Locator categoryHome = page.getByTestId("category-home");
  private final Locator categoryWork = page.getByTestId("category-work");
  private final Locator categoryPersonal = page.getByTestId("category-personal");
  private final Locator viewNote = page.getByTestId("note-view");
  private final Locator editNote = page.getByTestId("note-edit");
  private final Locator deleteNote = page.getByTestId("note-delete");
  private final Locator loader = page.locator(".spinner-border");

  public MyNotesHomePage(Page page) {
    super(page);
    this.header = new HeaderComponent(page);
  }

  @Override
  protected String path() {
    return UIRotes.HOME;
  }

  @Step("Click '+Add Note' button")
  public NoteModal openAddNoteModal() {
    addNoteButton.click();
    return new NoteModal(page);
  }

  public NoteComponent getNoteComponent(Note note) {
    noteRoot
        .filter(new Locator.FilterOptions().setHasText(note.getTitle()))
        .filter(new Locator.FilterOptions().setHasText(note.getDescription()));

    noteRoot.first().waitFor();

    return new NoteComponent(noteRoot.first());
  }

  /**
   * Waits for the loading spinner to disappear from the page.
   * This ensures the UI has finished updating after an action.
   */
  @Step("Wait for loading spinner to disappear")
  public void waitForLoaderToDisappear() {
    loader.waitFor(
        new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
  }

  /**
   * Checks that the note card with the specified title is not present on the page.
   * * @param title the title of the note to check
   */
  @Step("Verify that note with title '{title}' is deleted")
  public void verifyNoteIsDeleted(Note note) {
    Locator deletedNote = noteRoot.filter(new Locator.FilterOptions().setHasText(note.getTitle()));

    if (deletedNote.count() != 0) {
      throw new IllegalArgumentException(String.format(
          "Note with title '%s' was expected to be deleted, but it is still present!",
          note.getTitle()));
    }
  }
}
