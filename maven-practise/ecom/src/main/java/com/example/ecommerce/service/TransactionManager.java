package com.example.ecommerce.service;

import com.example.ecommerce.config.ConnectionFactory;
import com.example.ecommerce.exception.DataAccessException;

import java.sql.Connection;
import java.sql.SQLException;

public final class TransactionManager implements TransactionExecutor {
    private final ConnectionFactory connectionFactory;

    public TransactionManager(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public <T> T execute(TransactionCallback<T> callback) {
        try (Connection connection = connectionFactory.getConnection()) {
            boolean originalAutoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            try {
                T result = callback.execute(connection);
                connection.commit();
                return result;
            } catch (Exception e) {
                rollbackQuietly(connection);
                throw e;
            } finally {
                connection.setAutoCommit(originalAutoCommit);
            }
        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new DataAccessException("Transaction failed: " + e.getMessage(), e);
        }
    }

    private void rollbackQuietly(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException rollbackError) {
            throw new DataAccessException("Rollback failed", rollbackError);
        }
    }
}
