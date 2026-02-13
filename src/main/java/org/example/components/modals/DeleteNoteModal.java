package org.example.components.modals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DeleteNoteModal {
  private Locator deleteNoteHeader;
  private Locator deleteNoteButton;
  private Locator cancelDeleteButton;
  private Locator closeModalButton;

  public DeleteNoteModal(Page page) {
    this.deleteNoteHeader = page.getByTestId("Delete note?");
    this.deleteNoteButton = page.getByTestId("note-delete-confirm");
    this.closeModalButton = page.getByTestId("note-delete-cancel-1");
    this.cancelDeleteButton = page.getByTestId("note-delete-cancel-2");
  }

  public void isDeleteNoteModalDisplayed() {
       deleteNoteHeader.waitFor();
       deleteNoteHeader.isVisible();
  }

  public void deleteNote() {
    deleteNoteButton.click();
  }

  public void setCancelDeleteNote() {
    cancelDeleteButton.click();
  }

  public void closeDeleteModal() {
    closeModalButton.click();
  }
}


