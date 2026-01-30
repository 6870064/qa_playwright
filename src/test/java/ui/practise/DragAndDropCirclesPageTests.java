package ui.practise;

import io.qameta.allure.Description;
import org.example.enums.CircleColor;
import org.example.pages.practice.DragAndDropCirclesPage;
import org.example.pages.practice.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ui.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DragAndDropCirclesPageTests extends BaseTest {


  @ParameterizedTest(name = "{0}")
  @EnumSource(CircleColor.class)
  @DisplayName("[UI]. Drag and drop circles page. Drop a circle with a color ")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Drag and Drop Circles' page.
      3. Drag a circle to the drop zone.
      4. Assert a response.
      """)
  public void dragAllColorsTest(CircleColor color) {
    HomePage homePage = new HomePage(page()).open();
    DragAndDropCirclesPage dragAndDropCirclesPage = homePage.goToDragAndDropCircles();
    dragAndDropCirclesPage.dragCircleToDropZone(color.name());
    assertTrue(dragAndDropCirclesPage.isColorInDropZone(color.name()));
  }

  @DisplayName("[UI]. Drag and drop circles page. Drop a circle with a color ")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Drag and Drop Circles' page.
      3. Drag a 'red' circle to the drop zone.
      4. Assert amount of circles dropped to a drop zone.
      5. Drag a 'green' circle to the drop zone.
      6. Assert amount of circles dropped to a drop zone.
      7. Drag a 'blue' circle to the drop zone.
      8. Assert amount of circles dropped to a drop zone.
      """)
  @Test
  void allCircleAreDropped() {
    HomePage homePage = new HomePage(page()).open();
    DragAndDropCirclesPage dragAndDropCirclesPage = homePage.goToDragAndDropCircles();
    dragAndDropCirclesPage.dragCircleToDropZone(CircleColor.red.name());
    assertEquals(1, dragAndDropCirclesPage.droppedItemsCount());

    dragAndDropCirclesPage.dragCircleToDropZone(CircleColor.green.name());
    assertEquals(2, dragAndDropCirclesPage.droppedItemsCount());

    dragAndDropCirclesPage.dragCircleToDropZone(CircleColor.blue.name());
    assertEquals(3, dragAndDropCirclesPage.droppedItemsCount());
  }
}
