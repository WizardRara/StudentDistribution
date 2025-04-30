package org.example.repository;

import org.example.Exeption.TransactionException;
import org.example.config.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private final DBConnection dbConnection;

    public TransactionManager(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public <T> T doInTransaction(TransactionCallback<T> callback, boolean readOnly) {
        Connection connection = null;
        try {
            connection = dbConnection.getConnection();
            connection.setAutoCommit(false);
            connection.setReadOnly(readOnly);
            T result = callback.doInTransaction(connection);
            connection.commit();
            return result;
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    e.addSuppressed(rollbackEx);
                }
            }
            throw new TransactionException("Transaction failed ", e);
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException closeEx) {
                    throw new TransactionException("Failed to reset auto-commit or close connection ", closeEx);
                }
            }
        }
    }
}
