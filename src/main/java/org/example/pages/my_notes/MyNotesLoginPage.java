package org.example.pages.my_notes;

import com.microsoft.playwright.Page;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

public class MyNotesLoginPage extends BasePage {

  public MyNotesLoginPage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return UIRotes.LOGIN;
  }
}
