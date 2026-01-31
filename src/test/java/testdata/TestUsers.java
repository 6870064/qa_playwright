package testdata;

import org.example.objects.User;

public class TestUsers {

  public static User validUser() {
    return new User(
        "testUser24@mail.com",
        "testUser24",
        "qwerty_98",
        "qwerty_98"
    );
  }
}
