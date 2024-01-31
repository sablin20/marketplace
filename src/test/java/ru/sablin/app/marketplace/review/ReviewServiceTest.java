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
        review.setId(20);
        review.setClientName("Sem");
        review.setStoreId(2);
        review.setMessage("некачественный товар не советую");
        review.setRating(0);

        when(repository.leaveFeedback(review)).thenReturn(review);

        var result = service.leaveFeedback(review);

        assertNotNull(result);
        assertEquals(result, review);
    }

    @Test
    void getAvgRatingLast10ReviewAndTop3WordBeforeProduct() {
        var review1 = new Review();
        review1.setId(1);
        review1.setClientName("Alex");
        review1.setStoreId(1);
        review1.setMessage("крутой товар");
        review1.setRating(5);

        var review2 = new Review();
        review2.setId(2);
        review2.setClientName("Alex");
        review2.setStoreId(1);
        review2.setMessage("крутой товар");
        review2.setRating(5);

        var review3 = new Review();
        review3.setId(3);
        review3.setClientName("Alex");
        review3.setStoreId(1);
        review3.setMessage("крутой товар");
        review3.setRating(5);

        var review4 = new Review();
        review4.setId(4);
        review4.setClientName("Alex");
        review4.setStoreId(1);
        review4.setMessage("крутой товар");
        review4.setRating(5);

        var review5 = new Review();
        review5.setId(5);
        review5.setClientName("Alex");
        review5.setStoreId(1);
        review5.setMessage("крутой товар");
        review5.setRating(5);

        var review6 = new Review();
        review6.setId(6);
        review6.setClientName("Alex");
        review6.setStoreId(1);
        review6.setMessage("лучший товар");
        review6.setRating(5);

        var review7 = new Review();
        review7.setId(7);
        review7.setClientName("Alex");
        review7.setStoreId(1);
        review7.setMessage("лучший товар");
        review7.setRating(5);

        var review8 = new Review();
        review8.setId(8);
        review8.setClientName("Alex");
        review8.setStoreId(1);
        review8.setMessage("лучший товар");
        review8.setRating(5);

        var review9 = new Review();
        review9.setId(9);
        review9.setClientName("Alex");
        review9.setStoreId(1);
        review9.setMessage("плохой товар");
        review9.setRating(5);

        var review10 = new Review();
        review10.setId(10);
        review10.setClientName("Alex");
        review10.setStoreId(1);
        review10.setMessage("плохой товар");
        review10.setRating(5);

        var listReview = List.of(review1,review2,review3,review4,review5,review6,review7,review8,review9,review10);
        var avgRating = "Average rating last 10 review = 5";
        var top_1 = "Top_1 word before {товар}: крутой=5";
        var top_2 = "Top_2 word before {товар}: лучший=3";
        var top_3 = "Top_3 word before {товар}: плохой=2";

        when(repository.find10LastReview()).thenReturn(listReview);

        var result = service.getAvgRatingLast10ReviewAndTop3WordBeforeProduct();

        assertNotNull(result);
        assertTrue(result.contains(avgRating));
        assertTrue(result.contains(top_1));
        assertTrue(result.contains(top_2));
        assertTrue(result.contains(top_3));
    }
}