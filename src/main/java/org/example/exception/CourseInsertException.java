package org.example.exception;

public class CourseInsertException extends DatabaseException {
  public CourseInsertException(String message, Throwable cause) {
    super(message, cause);
  }
  public CourseInsertException(String message) {
    super(message, null);
  }
}
