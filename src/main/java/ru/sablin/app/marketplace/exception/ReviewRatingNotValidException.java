package ru.sablin.app.marketplace.exception;

public class ReviewRatingNotValidException extends RuntimeException {
    public ReviewRatingNotValidException(String message) {
        super(message);
    }
}
