package org.example.pages.my_notes;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.checkerframework.checker.units.qual.N;
import org.example.components.HeaderComponent;
import org.example.components.NoteComponent;
import org.example.components.modals.NoteModal;
import org.example.constants.routes.UIRotes;
import org.example.objects.Note;
import org.example.pages.BasePage;

import java.util.ArrayList;
import java.util.List;

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

}
