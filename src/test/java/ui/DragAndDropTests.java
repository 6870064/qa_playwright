package ui;

import io.qameta.allure.Description;
import org.example.pages.DragAndDropPage;
import org.example.pages.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DragAndDropTests extends BaseTest {


  @DisplayName("[UI]. Radio Buttons page. Validate possibility to check different color radio buttons")
  @Description("""
      1. Open https://practice.expandtesting.com/.
      2. Open 'Drag and Drop' page.
      3. Drag column 'A' to column 'B'.
      4. Assert that 'A' is dropped to 'B'.
      5. Drag column 'B' to column 'A'.
      6. Assert that 'B' is dropped to 'A'.
      """)
  @Test
  public void dragAndDropTest() {
    HomePage homePage = new HomePage(page()).open();
    DragAndDropPage dragAndDropPage = homePage.goToDragAndDrop();
    dragAndDropPage.dragAtoB();
    assertEquals("B", dragAndDropPage.getTextInA());
    assertEquals("A", dragAndDropPage.getTextInB());
    dragAndDropPage.dragBtoA();
    assertEquals("A", dragAndDropPage.getTextInA());
    assertEquals("B", dragAndDropPage.getTextInB());
  }
}
