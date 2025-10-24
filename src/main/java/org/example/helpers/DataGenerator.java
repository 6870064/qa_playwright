package org.example.helpers;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
  String fakeName;
  private static final Random random = new Random();
  private static final String ALPHANUMERIC =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final String NUMERIC =
      "1234567890";

  public String generateRandomName(int minLength, int maxLength) {
    do {
      if (minLength > 30) {
        fakeName = new Faker().name().lastName() + new Faker().name().name() + new Faker().name().name();
      }
      else {
        fakeName = new Faker().name().firstName();
      }
    } while ((fakeName.length() < minLength || fakeName.length() > maxLength));
    return fakeName;
  }

  public String generateRandomEmail(boolean isValid) {
    if(!isValid) {
      return "invalidEmail";
    }
    return new Faker().artist().name().replaceAll("\\s+", "_") + System.currentTimeMillis() + "@gmail.com";
  }

  public String generateRandomPassword(int minLength, int maxLength) {
    return new Faker().internet().password(minLength, maxLength);
  }

  public int generateRandomInt(int minLength, int maxLength) {
    return ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
  }

  public int generateRandomInt(int length) {
    if(length <=0) {
      throw new IllegalArgumentException("Lenght must be positive");
    }

    int minLength = (int) Math.pow(10, length - 1);
    int maxLength = (int) Math.pow(10, length) - 1;

    return ThreadLocalRandom.current().nextInt(minLength, maxLength + 1);
  }

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
   * Generates a random alphanumeric string of given length.
   *
   * @param length desired string length
   * @return random alphanumeric string of given length
   */
  protected static String randomAlphaNumeric(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      sb.append(ALPHANUMERIC.charAt(random.nextInt(ALPHANUMERIC.length())));
    }
    return sb.toString();
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


}
