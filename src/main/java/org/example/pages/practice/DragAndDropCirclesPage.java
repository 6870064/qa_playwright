package org.example.pages.practice;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.pages.BasePage;

public class DragAndDropCirclesPage extends BasePage {
  private final Locator dropZone = page.locator("#target");

  public DragAndDropCirclesPage(Page page) {
    super(page);
  }

  private final Locator circleByColor(String color) {
    return page.locator("source > div." + color + ", #target > div." + color);
  }

  @Override
  protected String path() {
    return "/drag-and-drop-circles";
  }

  private Locator circleInSource(String color) {
    return page.locator("#source > div." + color);
  }

  private Locator circleInTarget(String color) {
    return page.locator("#target > div." + color);
  }

  @Step("Drag circle '{color}' to drop zone")
  public void dragCircleToDropZone(String color) {
    circleInSource(color).dragTo(dropZone);
  }

  @Step("Check circle '{color}' is in drop zone")
  public boolean isColorInDropZone(String color) {
    Locator inTarget = circleInTarget(color);
    inTarget.waitFor(new Locator.WaitForOptions().setTimeout(5000));
    return inTarget.count() == 1;
  }

  @Step("Count dropped circles")
  public int droppedItemsCount() {
    return page.locator("#target > div[draggable='true']").count();
  }
}
