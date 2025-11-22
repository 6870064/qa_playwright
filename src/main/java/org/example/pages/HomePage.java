package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.components.FlashAlert;

public class HomePage extends BasePage {
  public HomePage(Page page) {
    super(page);
  }

  private final Locator webInputsLink = page.getByRole(
      AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Web inputs")
  );

  private final Locator registrationLink = page.getByRole(
      AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Test Register Page")
  );

  private final Locator loginPageLink = page.getByRole(
      AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Test Login Page")
  );

  public LoginPage goToLogin() {
    waitInterstitialAdToDisappear();

    // 2. Гарантируем, что линк видимый перед кликом
    loginPageLink.waitFor(new Locator.WaitForOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(5000));

    loginPageLink.click();

    page.waitForURL("**/login",
        new Page.WaitForURLOptions().setTimeout(7000)
    );

    page.waitForSelector("//h1[contains(.,'Test Login page')]",
        new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(5000)
    );

    return new LoginPage(page, new FlashAlert(page));
  }

  public RegisterPage goToRegister() {
    waitInterstitialAdToDisappear();
    registrationLink.waitFor(new Locator.WaitForOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(5000));

    registrationLink.click();
    page.waitForURL("**/register",
        new Page.WaitForURLOptions().setTimeout(7000));

// 5. Контрольная проверка контента страницы Inputs
    page.waitForSelector("//h1[contains(.,'Test Register page')]",
        new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(5000)
    );

    return new RegisterPage(page);
  }

  public WebInputsPage goToWebInputs() {
    // 1. Ждём, пока исчезнет межстраничная реклама и страница реально кликабельна
    waitInterstitialAdToDisappear();

    // 2. Гарантируем, что линк видимый перед кликом
    webInputsLink.waitFor(new Locator.WaitForOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(5000));

    // 3. Кликаем
    webInputsLink.click();

    // 4. Ждём смену URL на /inputs (до 7 сек)
    page.waitForURL("**/inputs",
        new Page.WaitForURLOptions().setTimeout(7000));

    // 5. Контрольная проверка контента страницы Inputs
    page.waitForSelector("//h1[contains(.,'Web inputs page')]",
        new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(5000)
    );

    return new WebInputsPage(page);
  }

  private void waitInterstitialAdToDisappear() {
    // ждём, пока НЕТ оверлея с рекламой google_vignette/adtech_redirect
    page.waitForSelector(
        "body:not(:has(#google_vignette)):not(:has(#adtech_redirect))",
        new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(5000)
    );
  }

  @Override
  protected String path() {
    return "/";
  }
 }
