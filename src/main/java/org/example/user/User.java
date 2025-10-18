package org.example.user;

public class User {
  String username;
  String password;
  String confirmPassword;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.confirmPassword = password;
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
