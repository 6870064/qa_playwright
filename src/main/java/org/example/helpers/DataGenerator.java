package org.example.helpers;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import org.example.enums.NoteCategory;
import org.example.objects.Note;
import org.example.requests.ApiNote;
import org.example.requests.UpdateApiNote;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility class for generating randomized test data for UI and API tests.
 * Utilizes JavaFaker and standard random generators to create realistic data objects.
 */
public class DataGenerator {
  public static final String LOCALE = "en";

  private static final Faker faker = new Faker(new Locale(LOCALE));
  private static final SecureRandom RANDOM = new SecureRandom();
  private static final AtomicInteger COUNTER = new AtomicInteger(RANDOM.nextInt(0x1000000));
  private static final Random random = new Random();
  private static final String ALPHANUMERIC =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final String NUMERIC =
      "1234567890";
  String fakeName;

  /**
   * Generates a random lowercase string of the given length.
   *
   * @param length length of the resulting string
   * @return random string consisting of lowercase Latin letters
   */
  public static String randomString(int length) {
    return random.ints('a', 'z' + 1)
        .limit(length)
        .collect(StringBuilder::new,
            StringBuilder::appendCodePoint,
            StringBuilder::append)
        .toString();
  }

  /**
   * Generates a random numeric string of given length.
   *
   * @param length desired string length
   * @return random numeric string of given length
   */
  protected static String randomNumeric(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(NUMERIC.charAt(random.nextInt(NUMERIC.length())));
    }
    return sb.toString();
  }

  /**
   * Generates a random date string between two provided dates.
   *
   * @param startDate the start boundary in yyyy-MM-dd format
   * @param endDate   the end boundary in yyyy-MM-dd format
   * @return a formatted date string
   */
  @Step("Generate random date between '{startDate}' and '{endDate}'")
  public static String generateRandomDate(String startDate, String endDate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate start = LocalDate.parse(startDate, formatter);
    LocalDate end = LocalDate.parse(endDate, formatter);

    long startEpochDay = start.toEpochDay();
    long endEpochDay = end.toEpochDay();
    long randomDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay);

    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
    return randomDate.format(formatter);
  }

  /**
   * Generates a new ApiNote for API requests with configurable constraints.
   *
   * @param titleLength           desired length of the title in characters
   * @param descriptionWordsCount number of words in the description
   * @return new generated ApiNote
   */
  @Step("Generate new API note with title length {titleLength}")
  public static ApiNote generateNewApiNote(int titleLength, int descriptionWordsCount) {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    String rawTitle = faker.book().title();
    String title = rawTitle.length() > titleLength
        ? rawTitle.substring(0, titleLength)
        : rawTitle + " " + faker.lorem().word();

    String description = faker.lorem().sentence(descriptionWordsCount);

    NoteCategory category = NoteCategory.values()[random.nextInt(NoteCategory.values().length)];

    return new ApiNote(title.trim(), description, category);
  }

  /**
   * Generates a new Note object for UI interactions.
   *
   * @param isCompleted           completion status of the note
   * @param titleLength           desired title length
   * @param descriptionWordsCount number of words in description
   * @return generated Note object
   */
  @Step("Generate new Note object (completed: {isCompleted})")
  public static Note generateNewNote(boolean isCompleted,
                                     int titleLength,
                                     int descriptionWordsCount) {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    String rawTitle = faker.book().title();
    String title = rawTitle.length() > titleLength
        ? rawTitle.substring(0, titleLength)
        : rawTitle + " " + faker.lorem().word();

    String description = faker.lorem().sentence(descriptionWordsCount);

    NoteCategory category = NoteCategory.values()[random.nextInt(NoteCategory.values().length)];

    return new Note(category, isCompleted, title, description);
  }

  /**
   * Generates a new Note object with a specific category.
   *
   * @param category              specified NoteCategory
   * @param isCompleted           completion status
   * @param titleLength           desired title length
   * @param descriptionWordsCount number of words in description
   * @return generated Note object
   */
  @Step("Generate new Note for category: {category}")
  public static Note generateNewNote(NoteCategory category,
                                     boolean isCompleted,
                                     int titleLength,
                                     int descriptionWordsCount) {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    String rawTitle = faker.book().title();
    String title = rawTitle.length() > titleLength
        ? rawTitle.substring(0, titleLength)
        : rawTitle + " " + faker.lorem().word();

    String description = faker.lorem().sentence(descriptionWordsCount);

    return new Note(category, isCompleted, title, description);
  }

  /**
   * Generates an UpdateApiNote object for API update requests.
   *
   * @param titleLength           desired title length
   * @param descriptionWordsCount word count for description
   * @param completed             completion status
   * @return generated UpdateApiNote object
   */
  @Step("Generate API update note")
  public static UpdateApiNote generateUpdateApiNote(
      int titleLength,
      int descriptionWordsCount,
      boolean completed) {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    String rawTitle = faker.book().title();
    String title = rawTitle.length() > titleLength
        ? rawTitle.substring(0, titleLength)
        : rawTitle + " " + faker.lorem().word();

    String description = faker.lorem().sentence(descriptionWordsCount);

    NoteCategory category = NoteCategory.values()[random.nextInt(NoteCategory.values().length)];

    return new UpdateApiNote(title.trim(), description, completed, category);
  }

  /**
   * Generates a random hexadecimal ID mimicking a MongoDB ObjectId.
   *
   * @return a 24-character hex string
   */
  @Step("Generate random hex ID")
  public static String generateRandomId() {
    byte[] bytes = new byte[12];

    int timestamp = (int) Instant.now().getEpochSecond();
    bytes[0] = (byte) ((timestamp >> 24) & 0xFF);
    bytes[1] = (byte) ((timestamp >> 16) & 0xFF);
    bytes[2] = (byte) ((timestamp >> 8) & 0xFF);
    bytes[3] = (byte) (timestamp & 0xFF);

    byte[] random5 = new byte[5];
    RANDOM.nextBytes(random5);
    System.arraycopy(random5, 0, bytes, 4, 5);

    int counter = COUNTER.getAndUpdate(i -> (i + 1) & 0xFFFFFF);
    bytes[9] = (byte) ((counter >> 16) & 0xFF);
    bytes[10] = (byte) ((counter >> 8) & 0xFF);
    bytes[11] = (byte) (counter & 0xFF);

    return toHex(bytes);
  }

  /**
   * Converts a byte array to a hexadecimal string.
   *
   * @param bytes byte array to convert
   * @return hexadecimal string representation
   */
  private static String toHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2] = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }

  /**
   * Generates a random name within the specified length range.
   *
   * @param minLength minimum character length
   * @param maxLength maximum character length
   * @return a random name string
   */
  @Step("Generate random name between {minLength} and {maxLength} chars")
  public String generateRandomName(int minLength, int maxLength) {
    do {
      if (minLength > 30) {
        fakeName = new Faker().name().name();
      } else {
        fakeName = new Faker().name().firstName();
      }
    } while ((fakeName.length() < minLength || fakeName.length() > maxLength));
    return fakeName;
  }

  /**
   * Generates a random email address.
   *
   * @param isValid if false, returns a known invalid email format
   * @return an email string
   */
  @Step("Generate random email (valid: {isValid})")
  public String generateRandomEmail(boolean isValid) {
    if (!isValid) {
      return "invalidEmail";
    }
    return new Faker().internet().safeEmailAddress();
  }

  /**
   * Generates a random password.
   *
   * @param minLength minimum length
   * @param maxLength maximum length
   * @return a random password string
   */
  @Step("Generate random password")
  public String generateRandomPassword(int minLength, int maxLength) {
    return new Faker().internet().password(minLength, maxLength);
  }

  /**
   * Generates a random integer within a range.
   *
   * @param minLength lower bound (inclusive)
   * @param maxLength upper bound (inclusive)
   * @return a random integer
   */
  public int generateRandomInt(int minLength, int maxLength) {
    return ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
  }

  /**
   * Generates a random integer with a specific number of digits.
   *
   * @param length number of digits
   * @return a random integer of specified digit length
   */
  public int generateRandomInt(int length) {
    if (length <= 0) {
      throw new IllegalArgumentException("Lenght must be positive");
    }

    int minLength = (int) Math.pow(10, length - 1);
    int maxLength = (int) Math.pow(10, length) - 1;

    return ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
  }

  /**
   * Generates a random phone number in E.164-like format.
   *
   * @param countryCode numeric country code without '+'
   * @return a phone number string starting with '+'
   */
  @Step("Generate random phone number for country code: {countryCode}")
  public String generateRandomPhoneNumber(String countryCode) {
    if (countryCode == null || !countryCode.matches("\\d+")) {
      throw new IllegalArgumentException("Country code must contain digits only");
    }

    int minTotalLength = 10;
    int maxTotalLength = 15;

    int totalLength = ThreadLocalRandom.current()
        .nextInt(minTotalLength, maxTotalLength + 1);

    int localNumberLength = totalLength - countryCode.length();
    if (localNumberLength <= 0) {
      throw new IllegalArgumentException("Country code is too long");
    }

    String localNumber = randomNumeric(localNumberLength);

    return "+" + countryCode + localNumber;
  }

  /**
   * Generates a random alphanumeric string.
   *
   * @param length desired length
   * @return random alphanumeric string
   */
  @Step("Generate random alphanumeric string of length {length}")
  public String randomAlphaNumeric(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
    }
    return sb.toString();
  }

  /**
   * Generates a random company name.
   *
   * @return a company name string
   */
  @Step("Generate random company name")
  public String generateRandomCompanyName() {
    return faker.company().name();
  }
}