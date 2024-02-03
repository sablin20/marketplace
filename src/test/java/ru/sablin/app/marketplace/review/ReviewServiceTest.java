package ru.sablin.app.marketplace.review;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    ReviewRepositoryImpl repository;

    @InjectMocks
    ReviewService service;

    @Test
    void leaveFeedback() {
        var review = new Review();
        review.setId(99);
        review.setClientName("Sem");
        review.setStoreName("DNS");
        review.setMessage("некачественный товар не советую");
        review.setRating(0);

        when(repository.leaveFeedback(review)).thenReturn(review);

        var result = service.leaveFeedback(review);

        assertNotNull(result);
        assertEquals(result, review);
    }

    Review createReview(Integer id,
                        String clientName,
                        String storeName,
                        String message,
                        Integer rating) {

        var review = new Review();
        review.setId(id);
        review.setClientName(clientName);
        review.setStoreName(storeName);
        review.setMessage(message);
        review.setRating(rating);
        return review;
    }

    @Test
    void getAvgRatingLast10ReviewAndTop3WordBeforeProduct() {
        var listReview = List.of(
                createReview(1,"Sem", "DNS", "крутой товар", 5),
                createReview(2,"Alex", "DNS", "крутой товар", 5),
                createReview(3,"Jack", "DNS", "крутой товар", 5),
                createReview(4,"Feel", "DNS", "крутой товар", 5),
                createReview(5,"Don", "DNS", "крутой товар", 5),
                createReview(6,"Leo", "DNS", "лучший товар", 5),
                createReview(7,"Smith", "DNS", "лучший товар", 5),
                createReview(8,"Den", "DNS", "лучший товар", 5),
                createReview(9,"Petr", "DNS", "плохой товар", 5),
                createReview(10,"Zak", "DNS", "плохой товар", 5));

        var avgRating = "StoreName = DNS : average rating last 10 review = 5";
        var top_1 = "Top_1 word before {товар}: крутой=5";
        var top_2 = "Top_2 word before {товар}: лучший=3";
        var top_3 = "Top_3 word before {товар}: плохой=2";

        when(repository.find10LastReview("DNS")).thenReturn(listReview);

        var result = service.getAvgRatingLast10ReviewAndTop3WordBeforeProduct("DNS");

        assertNotNull(result);
        assertTrue(result.contains(avgRating));
        assertTrue(result.contains(top_1));
        assertTrue(result.contains(top_2));
        assertTrue(result.contains(top_3));
    }
}