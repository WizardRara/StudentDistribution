package org.example.exception;

public class StudentSelectException extends DatabaseException {
  public StudentSelectException(String message, Throwable cause) {
    super(message, cause);
  }
  public StudentSelectException(String message) {
    super(message, null);
  }
}
