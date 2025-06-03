package org.example.exception;

public class CourseSelectException extends DatabaseException {
    public CourseSelectException(String message, Throwable cause) {
        super(message, cause);
    }
    public CourseSelectException(String message) {
        super(message, null);
    }
}
