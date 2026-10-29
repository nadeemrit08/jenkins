package com.example.ecommerce.service;

final class ImmediateTransactionExecutor implements TransactionExecutor {
    @Override
    public <T> T execute(TransactionCallback<T> callback) {
        try {
            return callback.execute(null);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
