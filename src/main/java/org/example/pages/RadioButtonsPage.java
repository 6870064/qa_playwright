package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.enums.RadioButtonColor;

public class RadioButtonsPage extends BasePage {
  private final String COLOR_RADIO_BUTTON = "input[name='color'][value='%s']";
  private final String SPORT_RADIO_BUTTON = "input[name='sport'][value='%s']";

  public RadioButtonsPage(Page page) {
    super(page);
  }

  @Override
  protected String path() {
    return "/radio-buttons";
  }

  public void selectColor(RadioButtonColor color) {
    page.locator(String.format(COLOR_RADIO_BUTTON, color)).check();
  }

  public RadioButtonColor getSelectedColor() {
    String value = page.locator("input[name='color']:checked").getAttribute("value");

    return RadioButtonColor.valueOf(value);
  }

  public void selectSport(String sport) {
    page.locator(String.format(SPORT_RADIO_BUTTON, sport)).check();
  }

  public String getSelectedSport() {
    return page.locator("input[name='sport']:checked").getAttribute("value");
  }

  public boolean isColorDisabled(RadioButtonColor color) {
    return page.locator(String.format(COLOR_RADIO_BUTTON, color)).isDisabled();
  }
}
