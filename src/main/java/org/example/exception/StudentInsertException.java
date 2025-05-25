package org.example.exception;

public class StudentInsertException extends DatabaseException {
    public StudentInsertException(String message, Throwable cause) {
        super(message, cause);
    }
    public StudentInsertException(String message) {
      super(message, null);
    }
}
