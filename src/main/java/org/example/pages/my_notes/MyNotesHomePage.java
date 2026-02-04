package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.components.HeaderComponent;
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
  private final Locator createNoteCategory = page.getByTestId("note-category");
  private final Locator createNoteCompleted = page.getByTestId("note-completed");
  private final Locator createNoteTitle = page.getByTestId("note-title");
  private final Locator createNoteDescription = page.getByTestId("note-description");
  private final Locator createNoteSubmit = page.getByTestId("note-submit");
  private final Locator createNoteCancel = page.getByTestId("note-cancel");

  public MyNotesHomePage(Page page) {
    super(page);
    this.header = new HeaderComponent(page);
  }

  @Override
  protected String path() {
    return UIRotes.HOME;
  }

}
