package ru.sablin.app.marketplace.review;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository {
    Review leaveFeedback(Review review);
    List<Review> find10LastReview(String storeName);
}
