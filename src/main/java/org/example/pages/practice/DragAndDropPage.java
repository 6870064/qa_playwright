package org.example.pages.practice;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.pages.BasePage;

public class DragAndDropPage extends BasePage {
  private final Locator columnA = page.locator("#column-a");
  private final Locator columnB = page.locator("#column-b");

  public DragAndDropPage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return "/drag-and-drop";
  }

  @Step("Drag column A to column B")
  public void dragAtoB() {
    columnA.dragTo(columnB);
  }

  @Step("Drag column B to column A")
  public void dragBtoA() {
    columnB.dragTo(columnA);
  }

  @Step("Get value from column A")
  public String getTextInA() {
    return columnA.innerText().trim();
  }

  @Step("Get value from column B")
  public String getTextInB() {
    return columnB.innerText().trim();
  }
}
