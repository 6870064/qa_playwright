package org.example.pages.my_notes;

import com.microsoft.playwright.Page;
import org.example.components.HeaderComponent;
import org.example.constants.routes.UIRotes;
import org.example.pages.BasePage;

public class MyNotesHomePage extends BasePage {
  public final HeaderComponent header;

  public MyNotesHomePage(Page page) {
    super(page);
    this.header = new HeaderComponent(page);
  }

  @Override
  protected String path() {
    return UIRotes.HOME;
  }

}
