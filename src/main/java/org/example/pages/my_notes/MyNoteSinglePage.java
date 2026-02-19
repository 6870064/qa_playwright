package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import org.example.components.HeaderComponent;
import org.example.components.modals.DeleteNoteModal;
import org.example.components.modals.NoteModal;
import org.example.objects.Note;
import org.example.pages.BasePage;
import org.joda.time.IllegalInstantException;

/**
 * Page Object representing the standalone view of a single note.
 * Provides functionality to view, edit, delete, and toggle the completion status of the specific note.
 */
public class MyNoteSinglePage extends BasePage {
  public final HeaderComponent header;
  private final Locator noteTitle = page.getByTestId("note-card-title");
  private final Locator noteDescription = page.getByTestId("note-card-description");

  private final Locator editButton = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Edit"));

  private final Locator deleteButton = page.getByRole(AriaRole.BUTTON,
      new Page.GetByRoleOptions().setName("Delete"));

  private final Locator completedCheckbox = page.getByTestId("toggle-note-switch");

  /**
   * Initializes the MyNoteSinglePage with the Playwright Page instance and waits for the page to load.
   *
   * @param page the Playwright Page instance to be used by all extending pages
   */
  public MyNoteSinglePage(Page page) {
    super(page);
    this.header = new HeaderComponent(page);
    waitForOpen();
  }

  /**
   * Clicks the 'Edit' button to open the note editor modal.
   * * @return a new instance of the NoteModal
   */
  @Step("Click 'Edit' button on the single note page")
  public NoteModal editNote() {
    editButton.click();
    return new NoteModal(page);
  }

  /**
   * Clicks the 'Delete' button to trigger the deletion confirmation modal.
   * * @return a new instance of the DeleteNoteModal
   */
  @Step("Click 'Delete' button on the single note page")
  public DeleteNoteModal deleteNote() {
    deleteButton.click();
    return new DeleteNoteModal(page);
  }

  /**
   * Toggles the completion status of the note.
   * If the note is completed, it will be unchecked, and vice versa.
   */
  @Step("Toggle note completion status")
  public void setStatus() {
    if (!this.isCompleted()) {
      completedCheckbox.check();
    } else {
      completedCheckbox.uncheck();
    }
  }

  /**
   * Verifies if the completion checkbox is currently checked.
   * * @return true if checked, false otherwise
   */
  @Step("Check if the completion toggle is selected")
  public boolean isCompleted() {
    return completedCheckbox.isChecked();
  }

  /**
   * Retrieves the title of the note displayed on the page.
   * * @return the trimmed title string
   */
  @Step("Get note title from the single page")
  public String getTitle() {
    return noteTitle.innerText().trim();
  }

  /**
   * Retrieves the description of the note displayed on the page.
   * * @return the trimmed description string
   */
  @Step("Get note description from the single page")
  public String getDescription() {
    return noteDescription.innerText().trim();
  }

  /**
   * Returns the relative URL path for the single note page.
   * * @return empty string as the path is typically dynamic
   */
  @Override
  protected String path() {
    return "";
  }

  public void compareNote(Note note) {
    if (!this.getTitle().equals(note.getTitle())) {
      throw new IllegalInstantException(String.format("Content of the title is not equal:" +
          "expected: %s;" +
          "actual: %s", note.getTitle(), this.getTitle()));
    }
    if (!this.getDescription().equals(note.getDescription())) {
      throw new IllegalInstantException(String.format("Content of the description is not equal:" +
          "expected: %s;" +
          "actual: %s", note.getDescription(), this.getDescription()));
    }

    if (this.isCompleted() != note.isCompleted()) {
      throw new IllegalInstantException(String.format("Status of the description is not equal:" +
          "expected: %s;" +
          "actual: %s", note.isCompleted(), this.isCompleted()));
    }
  }
}