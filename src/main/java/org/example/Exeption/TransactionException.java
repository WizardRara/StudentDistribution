package org.example.Exeption;

public class TransactionException extends DatabaseException {
    public TransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
