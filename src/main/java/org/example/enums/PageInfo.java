package org.example.enums;

import static org.example.constants.Constants.BASE_URL;

public enum PageInfo {
  WEB_INPUT("Web inputs","Web inputs", "/inputs"),
  SECURE("Test Login Page","Secure Area", "/secure"),
  LOGIN("Test Login Page","Test Login", "/login"),
  REGISTER("Test Register Page","Test Register", "register"),
  OTP_LOGIN("OTP: One Time Password","OTP Login", "/otp-login"),
  OTP_VERIFICATION("OTP: One Time Password","OTP Login", "/otp-verification");

  private final String linkTitle;
  private final String title;
  private final String path;

  PageInfo(String linkTitle, String title, String path) {
    this.linkTitle = linkTitle;
    this.title = title;
    this.path = path;
  }

  public String title() {
    return title;
  }

  public String path() {
    return path;
  }

  public String url() {
    return BASE_URL + path;
  }

  public String linkTitle() {
    return linkTitle;
  }
}
