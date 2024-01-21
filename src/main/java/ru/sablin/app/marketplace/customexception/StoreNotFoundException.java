package ru.sablin.app.marketplace.customexception;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(String message) {
        super(message);
    }
}
