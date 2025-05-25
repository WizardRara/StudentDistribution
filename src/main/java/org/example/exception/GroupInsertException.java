package org.example.exception;

public class GroupInsertException extends DatabaseException {
    public GroupInsertException(String message, Throwable cause) {
        super(message, cause);
    }
    public GroupInsertException(String message) {
        super(message, null);
    }
}
