package org.example.Exeption;

public class CourseInsertException extends DatabaseException {
  public CourseInsertException(String message, Throwable cause) {
    super(message, cause);
  }
  public CourseInsertException(String message) {
    super(message, null);
  }
}
