package org.example.pages;

import com.microsoft.playwright.Page;
import org.example.utils.AppConfig;

public abstract class BasePage {
  protected final Page page;

  public BasePage(Page page) {
    this.page = page;
  }

  /** Каждая страница указывает свой относительный путь */
  protected abstract String path();


  public <T extends BasePage> T open() {
    page.navigate(AppConfig.baseUrl() + path());
    return (T) this;

  }
}
