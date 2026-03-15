package ui.practise;

import org.example.pages.practice.DragAndDropPage;
import org.example.pages.practice.HomePage;
import org.junit.jupiter.api.Test;
import ui.BaseTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DragAndDropTests extends BaseTest {


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
