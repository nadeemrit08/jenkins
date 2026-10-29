package com.example.ecommerce.service;

public interface TransactionExecutor {
    <T> T execute(TransactionCallback<T> callback);
}
