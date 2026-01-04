package org.example.helpers;

public class AuthContent {
  private String accessToken;

  public AuthContent(ThreadLocal<String> accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }
}
