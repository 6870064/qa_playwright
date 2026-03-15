package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.components.HeaderComponent;
import org.example.components.NoteComponent;
import org.example.components.modals.DeleteNoteModal;
import org.example.components.modals.NoteModal;
import org.example.constants.routes.UIRotes;
import org.example.helpers.DataGenerator;
import org.example.objects.Note;
import org.example.pages.BasePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class MyNotesHomePage extends BasePage {
  public final HeaderComponent header;

  private final Locator noteRoot = page.getByTestId("note-card");
  private final Locator searchButton = page.getByTestId("search-btn");
  private final Locator searchInput = page.getByTestId("search-input");
  private final Locator addNoteButton = page.getByTestId("add-new-note");
  private final Locator categoryAll = page.getByTestId("category-all");
  private final Locator categoryHome = page.getByTestId("category-home");
  private final Locator categoryWork = page.getByTestId("category-work");
  private final Locator categoryPersonal = page.getByTestId("category-personal");
  private final Locator viewNote = page.getByTestId("note-view");
  private final Locator editNote = page.getByTestId("note-edit");
  private final Locator deleteNote = page.getByTestId("note-delete");
  private final Locator loader = page.locator(".spinner-border");
  private final Locator emptyStateMessage = page.getByTestId("no-notes-message");
  private final Locator infoMessage = page.getByTestId("progress-info");

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

  public NoteComponent searchNoteByTitle(Note note) {
    searchInput.fill(note.getTitle());
    searchButton.click();

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

  /**
   * Creates a specified number of notes to test system limits or pagination.
   * This method orchestrates NoteModal and NoteComponent internally.
   *
   * @param amount number of notes to create
   */
  @Step("Mass create {amount} notes and verify each")
  public void createMultipleNotes(int amount) {
    for (int i = 1; i <= amount; i++) {
      Note newNote = DataGenerator.generateNewNote(
          false,
          25,
          100);

      this.openAddNoteModal()
          .createNewNote(newNote);

      this.waitForLoaderToDisappear();

      this.getNoteComponent(newNote)
          .compareNote(newNote);
    }
  }

  @Step("Mass create {amount} notes and verify each")
  public void createNewNote(Note note) {

    this.openAddNoteModal()
        .createNewNote(note);

    this.waitForLoaderToDisappear();

    this.getNoteComponent(note)
        .compareNote(note);
  }

  /**
   * Returns the current number of note cards visible on the page.
   *
   * @return total count of notes.
   */
  @Step("Get total count of notes on the page")
  public int getNotesCount() {
    return noteRoot.count();
  }

  /**
   * Removes all notes currently visible on the page one by one.
   * <p>
   * The process involves clicking the delete button for the first available note,
   * confirming the action in the modal, and waiting for the UI loader to disappear.
   * Once the list is empty, it verifies that the "no notes" placeholder message is displayed.
   * </p>
   */
  @Step("Delete all notes and verify empty state message")
  public void deleteAllNotes() {
    categoryAll.click();
    noteRoot.first().waitFor();

    while (noteRoot.count() > 0) {
      Locator firstNote = noteRoot.first();
      NoteComponent NoteCard = new NoteComponent(firstNote);

      DeleteNoteModal modal = NoteCard.deleteNote();
      modal.deleteNote();

      this.waitForLoaderToDisappear();
    }
    assertThat(noteRoot).hasCount(0);
    assertThat(emptyStateMessage).isVisible();
    assertThat(emptyStateMessage).hasText("You don't have any notes in all categories");
  }

  /**
   * Waits for the page content to load by checking for either existing notes
   * or the empty state message.
   *
   * @return the same instance for method chaining.
   */
  @Step("Wait for notes content to load")
  public MyNotesHomePage waitForPageToLoad() {
    this.waitForLoaderToDisappear();
    noteRoot.or(emptyStateMessage).first().waitFor();
    return this;
  }


  /**
   * Clears the search input field and refreshes the results.
   * This method resets the search state by clearing the text and triggering a new search.
   */
  @Step("Clear search input and refresh results")
  public void clearSearchInput() {
    searchInput.clear();
    searchButton.click();
    waitForLoaderToDisappear();
  }

  /**
   * Selects a specific category and verifies the progress message updates accordingly.
   * * @param category The name of the category to select (e.g., "home", "work", "personal").
   */
  @Step("Select category: {category} and verify progress message")
  public void selectCategory(String category) {
    Locator categoryLocator = page.getByTestId(String.format("category-%s", category));
    categoryLocator.click();

   assertThat(infoMessage).containsText(String.format("completed in the %s category", category));
  }
}