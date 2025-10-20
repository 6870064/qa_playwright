package org.example.helpers;

import com.github.javafaker.Faker;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DataGenerator {
  String fakeName;

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
}
