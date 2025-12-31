package ui;

import org.example.pages.DynamicTablePage;
import org.example.pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicTableTests extends BaseTest {

  @Test
  public void compareChromeCpuTest() {
    HomePage homePage = new HomePage(page).open();
    DynamicTablePage dynamicTablePage = homePage.goToDynamicTable();

    String cpuFromTable = dynamicTablePage.getChromeFromTable();
    String cpuFromLabel = dynamicTablePage.getChromeCpuLabelValue();

    assertEquals(
        cpuFromTable,
        cpuFromLabel,
        "Chrome CPU mismatch:\nTable= " + cpuFromTable + "\nLabel = " + cpuFromLabel
    );
  }
}
