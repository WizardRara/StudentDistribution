package org.example.exception;

public class TransactionException extends DatabaseException {
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
