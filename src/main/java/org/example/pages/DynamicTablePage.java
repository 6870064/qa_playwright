package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public class DynamicTablePage extends BasePage {

  private final String tableHeaders = "table thead th";
  private final String tableRows = "table tbody tr";
  private final Locator chromeLabel;

  public DynamicTablePage(Page page) {
    super(page);
    this.chromeLabel = page.locator("#chrome-cpu");
  }

  @Override
  protected String path() {
    return "dynamic-table";
  }

  public String getChromeFromTable() {
    List<String> headers = page.locator(tableHeaders).allInnerTexts();

    int cpuColumnIndex = headers.indexOf("CPU") + 1;
    if (cpuColumnIndex == 0) {
      throw new RuntimeException("Column 'CPU' not found in table headers");
    }

    Locator chromeRow = page.locator(tableRows).filter(
        new Locator.FilterOptions().setHasText("Chrome"));

    if (chromeRow.count() == 0) {
      throw new RuntimeException("Row 'Chrome' not found in the table");
    }

    return chromeRow
        .locator("td:nth-child(" + cpuColumnIndex + ")")
        .innerText()
        .trim();
  }

  public String getChromeCpuLabelText() {
    return chromeLabel.innerText().trim();
  }

  public String getChromeCpuLabelValue() {
    return getChromeCpuLabelText()
        .replace("Chrome CPU", "")    // убираем без двоеточия
        .replace(":", "")             // убираем отдельно
        .trim();
  }
}
