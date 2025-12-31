package org.example.requests.user;

public class UiUser {
  String username;
  String password;
  String confirmPassword;

  public UiUser(String username, String password, String confirmPassword) {
    this.username = username;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }

  @Override
  public String toString() {
    return "User{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", confirmPassword='" + confirmPassword + '\'' +
        '}';
  }
}
