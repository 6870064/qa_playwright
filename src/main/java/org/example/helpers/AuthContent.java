package org.example.helpers;

public class AuthContent {
  private final ThreadLocal<String> accessToken;

  public AuthContent(ThreadLocal<String> accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return accessToken.get();
  }

  public void setAccessToken(String token) {
    accessToken.set(token);
  }

  public void clear() {
    accessToken.remove();
  }
}
