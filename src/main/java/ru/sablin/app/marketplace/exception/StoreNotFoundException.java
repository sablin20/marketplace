package ru.sablin.app.marketplace.exception;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(String message) {
        super(message);
    }
}
