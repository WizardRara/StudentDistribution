package org.example.Exeption;

public class StudentInsertException extends DatabaseException {
    public StudentInsertException(String message, Throwable cause) {
        super(message, cause);
    }
    public StudentInsertException(String message) {
      super(message, null);
    }
}
