package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import org.example.components.FlashAlert;

public class HomePage extends BasePage {
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
  private final Locator dynamicTablePageLink = page.getByRole(
      AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Dynamic Table")
  );
  private final Locator forgotPasswordFormLink = page.getByRole(
      AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Forgot Password Form")
  );

  private final Locator myBrowserInformationLink = page.getByRole(
      AriaRole.LINK,
      new Page.GetByRoleOptions().setName("My Browser Information")
  );

  private final Locator radioButtonsPageLink = page.getByRole(
      AriaRole.LINK,
      new Page.GetByRoleOptions().setName("Radio Buttons")
  );

  public HomePage(Page page) {
    super(page);
  }

  // 🔥 ЕДИНЫЙ метод переходов
  private void safeClickAndWait(Locator link, String urlPattern, String headerText) {
    waitInterstitialAdToDisappear();

    link.waitFor(new Locator.WaitForOptions()
        .setState(WaitForSelectorState.VISIBLE)
        .setTimeout(5000));

    link.click(new Locator.ClickOptions().setNoWaitAfter(true));

    page.waitForLoadState();

    page.waitForURL(urlPattern,
        new Page.WaitForURLOptions().setTimeout(7000));

    page.waitForSelector("//h1[contains(.,'" + headerText + "')]",
        new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(5000));
  }

  // ---------- ПЕРЕХОДЫ ----------
  @Step("Opening Login page")
  public LoginPage goToLogin() {
    safeClickAndWait(loginPageLink, "**/login", "Test Login page");
    return new LoginPage(page, new FlashAlert(page));
  }

  @Step("Opening Register page")
  public RegisterPage goToRegister() {
    safeClickAndWait(registrationLink, "**/register", "Test Register page");
    return new RegisterPage(page);
  }

  @Step("Opening Web Inputs page")
  public WebInputsPage goToWebInputs() {
    safeClickAndWait(webInputsLink, "**/inputs", "Web inputs page");
    return new WebInputsPage(page);
  }

  @Step("Opening Dynamic Table page")
  public DynamicTablePage goToDynamicTable() {
    safeClickAndWait(dynamicTablePageLink, "**/dynamic-table", "Dynamic Table page");
    return new DynamicTablePage(page);
  }

  @Step("Opening Forgot Password page")
  public ForgotPasswordPage goToForgotPassword() {
    safeClickAndWait(forgotPasswordFormLink, "**/forgot-password", "Dummy Forgot Password form page");
    return new ForgotPasswordPage(page);
  }

  @Step("Opening Radio Buttons page")
  public RadioButtonsPage goToRadioButtons() {
    safeClickAndWait(radioButtonsPageLink, "**/radio-buttons", "Radio Buttons page");
    return new RadioButtonsPage(page);
  }

  @Step("Opening Browser Information page")
  public MyBrowserInformationPage goMyBrowserInformation() {
    safeClickAndWait(myBrowserInformationLink, "**/my-browser", "My Browser Information page");
    return new MyBrowserInformationPage(page);
  }

  @Step("Waiting for Add Disappear")
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
