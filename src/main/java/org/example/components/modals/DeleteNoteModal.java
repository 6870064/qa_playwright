package org.example.components.modals;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class DeleteNoteModal {
  private final Locator deleteNoteHeader;
  private final Locator deleteNoteButton;
  private final Locator cancelDeleteButton;
  private final Locator closeModalButton;

  /**
   * Page object representing the confirmation modal for deleting a note.
   */
  public DeleteNoteModal(Page page) {
    this.deleteNoteHeader = page.getByTestId("Delete note?");
    this.deleteNoteButton = page.getByTestId("note-delete-confirm");
    this.closeModalButton = page.getByTestId("note-delete-cancel-1");
    this.cancelDeleteButton = page.getByTestId("note-delete-cancel-2");
  }

  /**
   * Verifies that the delete confirmation modal is visible on the page.
   */
  @Step("Check if 'Delete Note' modal is displayed")
  public void isDeleteNoteModalDisplayed() {
    deleteNoteHeader.waitFor();
    deleteNoteHeader.isVisible();
  }

  /**
   * Confirms the deletion of the selected note by clicking the delete button.
   */
  @Step("Confirm note deletion")
  public void deleteNote() {
    deleteNoteButton.click();
  }

  /**
   * Cancels the deletion process using the secondary cancel button.
   */
  @Step("Cancel note deletion via 'Cancel' button")
  public void setCancelDeleteNote() {
    cancelDeleteButton.click();
  }

  /**
   * Closes the delete modal using the close icon button.
   */
  @Step("Close delete modal via 'X' button")
  public void closeDeleteModal() {
    closeModalButton.click();
  }
}


