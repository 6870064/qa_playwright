package org.example.objects;

/**
 * Data Transfer Object (DTO) representing a system user.
 * Stores personal details, credentials, and contact information for authentication and profile management.
 */
public class User {
  private String userId;
  private String email;
  private String name;
  private String phoneNumber;
  private String companyName;
  private String password;
  private String confirmPassword;

  /**
   * Constructs a new User with mandatory registration fields.
   *
   * @param email           the user's email address
   * @param name            the user's full name
   * @param password        the user's account password
   * @param confirmPassword the password confirmation string
   */
  public User(String email, String name, String password, String confirmPassword) {
    this.email = email;
    this.name = name;
    this.password = password;
    this.confirmPassword = confirmPassword;
  }

  /**
   * Gets the unique identifier of the user.
   *
   * @return the user ID string
   */
  public String getUserId() {
    return userId;
  }

  /**
   * Sets the unique identifier for the user.
   *
   * @param userId the unique string ID to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }

  /**
   * Gets the user's email address.
   *
   * @return the email string
   */
  public String getEmail() {
    return email;
  }

  /**
   * Gets the user's full name.
   *
   * @return the name string
   */
  public String getName() {
    return name;
  }

  /**
   * Gets the user's account password.
   *
   * @return the password string
   */
  public String getPassword() {
    return password;
  }

  /**
   * Gets the password confirmation string.
   *
   * @return the confirm password string
   */
  public String getConfirmPassword() {
    return confirmPassword;
  }

  /**
   * Gets the user's phone number.
   *
   * @return the phone number string
   */
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Gets the user's company name.
   *
   * @return the company name string
   */
  public String getCompanyName() {
    return companyName;
  }

  /**
   * Sets or updates the user's full name.
   *
   * @param name the new name string
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Sets or updates the user's company name.
   *
   * @param companyName the new company name string
   */
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  /**
   * Sets or updates the user's phone number.
   *
   * @param phoneNumber the new phone number string
   */
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}