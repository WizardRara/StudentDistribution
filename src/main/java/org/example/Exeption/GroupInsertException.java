package org.example.Exeption;

public class GroupInsertException extends DatabaseException {
    public GroupInsertException(String message, Throwable cause) {
        super(message, cause);
    }
    public GroupInsertException(String message) {
        super(message, null);
    }
}
