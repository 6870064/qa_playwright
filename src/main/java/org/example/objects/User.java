package org.example.objects;

public class User {
  private String userId;
  private String email;
  private String name;
  private String phoneNumber;
  private String companyName;
  private String password;
  private String confirmPassword;

  public User(String email, String name, String password, String confirmPassword) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
