package org.example.helpers;

import com.github.javafaker.Faker;
import org.example.enums.Category;
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

public class DataGenerator {
  public static final String LOCALE = "en";

  private static final Faker faker = new Faker(new Locale(LOCALE));
  String fakeName;
  // SecureRandom для хорошей энтропии (5 байт)
  private static final SecureRandom RANDOM = new SecureRandom();

  // 3-байтный счётчик (в пределах 0..0xFFFFFF), Atomic для потокобезопасности
  private static final AtomicInteger COUNTER = new AtomicInteger(RANDOM.nextInt(0x1000000));

  private static final Random random = new Random();
  private static final String ALPHANUMERIC =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final String NUMERIC =
      "1234567890";

  public String generateRandomName(int minLength, int maxLength) {
    do {
      if (minLength > 30) {
        fakeName = new Faker().name().name();
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
  public static String randomAlphaNumeric(int length) {
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


  /**
   * Generates a random Note with configurable title length and description word count.
   *
   * @param titleLength           desired length of the title in characters
   * @param descriptionWordsCount number of words in the description
   * @return randomly generated Note
   */
  public static ApiNote generateNewRandomApiNote(int titleLength, int descriptionWordsCount) {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    // Генерация длинного заголовка, а затем его обрезка до нужной длины
    String rawTitle = faker.book().title();
    String title = rawTitle.length() > titleLength
        ? rawTitle.substring(0, titleLength)
        : rawTitle + " " + faker.lorem().word(); // если короткий — добавим слово

    String description = faker.lorem().sentence(descriptionWordsCount);

    Category category = Category.values()[random.nextInt(Category.values().length)];

    return new ApiNote(title.trim(), description, category);
  }

  public static UpdateApiNote generateUpdateApiNote(
      int titleLength,
      int descriptionWordsCount,
      boolean completed) {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    // Генерация длинного заголовка, а затем его обрезка до нужной длины
    String rawTitle = faker.book().title();
    String title = rawTitle.length() > titleLength
        ? rawTitle.substring(0, titleLength)
        : rawTitle + " " + faker.lorem().word(); // если короткий — добавим слово

    String description = faker.lorem().sentence(descriptionWordsCount);

    Category category = Category.values()[random.nextInt(Category.values().length)];

    return new UpdateApiNote(title.trim(), description, completed, category);
  }

  public static String generateRandomId() {
    byte[] bytes = new byte[12];

    // 1) 4 байта — текущее Unix time в секундах (big-endian)
    int timestamp = (int) Instant.now().getEpochSecond();
    bytes[0] = (byte) ((timestamp >> 24) & 0xFF);
    bytes[1] = (byte) ((timestamp >> 16) & 0xFF);
    bytes[2] = (byte) ((timestamp >> 8)  & 0xFF);
    bytes[3] = (byte) ( timestamp        & 0xFF);

    // 2) 5 байт — крипто-рандом (универсально заменяет machine+pid)
    byte[] random5 = new byte[5];
    RANDOM.nextBytes(random5);
    System.arraycopy(random5, 0, bytes, 4, 5);

    // 3) 3 байта — счётчик
    int counter = COUNTER.getAndUpdate(i -> (i + 1) & 0xFFFFFF);
    bytes[9]  = (byte) ((counter >> 16) & 0xFF);
    bytes[10] = (byte) ((counter >> 8)  & 0xFF);
    bytes[11] = (byte) ( counter        & 0xFF);

    return toHex(bytes);
  }

  private static String toHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    for (int j = 0; j < bytes.length; j++) {
      int v = bytes[j] & 0xFF;
      hexChars[j * 2]     = HEX_ARRAY[v >>> 4];
      hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
    }
    return new String(hexChars);
  }




}
