package org.example.exception;

public class GroupSelectException extends DatabaseException {
    public GroupSelectException(String message, Throwable cause) {
        super(message, cause);
    }
    public GroupSelectException(String message) {
        super(message, null);
    }
}
