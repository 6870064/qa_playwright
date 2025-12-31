package org.example.enums;

public enum HttpStatus {
  OK(200),
  CREATED(201),
  BAD_REQUEST(400),
  UNAUTHORIZED(401),
  NOT_FOUND(404);

  private final int code;

  HttpStatus(int code) {
    this.code = code;
  }

  public int code() {
    return code;
  }
}
