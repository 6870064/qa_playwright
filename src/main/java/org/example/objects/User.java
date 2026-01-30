package org.example.objects;

public class User {
  private String email;
  private String name;
  private String password;
  private String confirmPassword;

  public User(String email, String name, String password, String confirmPassword) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }
}
