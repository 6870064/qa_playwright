package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.components.HeaderComponent;
import org.example.components.modals.NoteModal;
import org.example.constants.routes.UIRotes;
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
  public NoteModal openAddNoteModal() {
    addNoteButton.click();
    return new NoteModal(page);
  }
}
