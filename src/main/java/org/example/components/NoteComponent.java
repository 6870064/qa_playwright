package org.example.components;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Step;
import org.example.components.modals.DeleteNoteModal;
import org.example.components.modals.NoteModal;
import org.example.enums.NoteCategory;
import org.example.objects.Note;
import org.example.pages.my_notes.MyNoteSinglePage;

import java.util.Map;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

/**
 * Component representing a single note card on the home page.
 * Provides methods to interact with and retrieve data from a specific note.
 */
public class NoteComponent {
  private static final String COMPLETED_COLOR = "rgb(173, 181, 189)";
  private static final Map<NoteCategory, String> CATEGORY_COLORS = Map.of(
      NoteCategory.Home, "rgb(255, 145, 0)",
      NoteCategory.Work, "rgb(92, 107, 192)",
      NoteCategory.Personal, "rgb(50, 140, 160)"
  );
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
  public MyNoteSinglePage viewNote() {
    viewButton.click();
    return new MyNoteSinglePage(root.page());
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
    this.deleteButton.click();
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
   * Toggles the completion status of the note.
   * If the note is completed, it will be unchecked, and vice versa.
   */
  @Step("Toggle note completion status")
  public void setStatus() {
    if (!this.isCompleted()) {
      isCompletedToggle.check();
    } else {
      isCompletedToggle.uncheck();
    }
  }

  /**
   * Checks if the note completion switch is currently checked.
   *
   * @return true if checked, false otherwise
   */
  public boolean isCompleted() {
    return isCompletedToggle.isChecked();
  }

  /**
   * Performs a comprehensive validation of the note component's state on the UI.
   * <p>
   * This method uses Web-First assertions to verify:
   * <ul>
   * <li>The visibility and correctness of the note title and description.</li>
   * <li>The background color of the title, which must match the {@link NoteCategory}
   * if the note is active, or become grey if it is completed.</li>
   * <li>The state of the completion checkbox.</li>
   * </ul>
   * * @param note the {@link Note} object containing the expected data to compare against the UI.
   *
   * @throws AssertionError if any of the UI elements do not match the expected state within the timeout.
   */
  @Step("Compare note UI state with expected data")
  public void compareNote(Note note) {
    assertThat(title).hasText(note.getTitle());
    assertThat(description).hasText(note.getDescription());

    if (note.isCompleted()) {
      assertThat(title).hasCSS("background-color", COMPLETED_COLOR);
    } else {
      String expectedRgb = CATEGORY_COLORS.get(note.getCategory());
      assertThat(title).hasCSS("background-color", expectedRgb);
    }

    if (note.isCompleted()) {
      assertThat(isCompletedToggle).isChecked();
    } else {
      assertThat(isCompletedToggle).not().isChecked();
    }
  }
}
