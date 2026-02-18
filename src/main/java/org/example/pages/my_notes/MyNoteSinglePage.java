package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.example.components.HeaderComponent;
import org.example.components.modals.DeleteNoteModal;
import org.example.components.modals.NoteModal;
import org.example.pages.BasePage;

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
   * Initializes the BasePage with the Playwright Page instance.
   *
   * @param page the Playwright Page instance to be used by all extending pages
   */
  public MyNoteSinglePage(Page page) {
    super(page);
    this.header = new HeaderComponent(page);
    waitForOpen();
  }

  public NoteModal editNote() {
    editButton.click();
    return new NoteModal(page);
  }

  public DeleteNoteModal deleteNote() {
    deleteButton.click();
    return new DeleteNoteModal(page);
  }

  public void completeNote() {
    completedCheckbox.check();
  }

  public boolean isCompleteNoteChecked() {
    return completedCheckbox.isChecked();
  }

  public String getNoteTitle() {
    return noteTitle.innerText().trim();
  }

  public String getNoteDescription() {
    return noteDescription.innerText().trim();
  }

  @Override
  protected String path() {
    return "";
  }
}
