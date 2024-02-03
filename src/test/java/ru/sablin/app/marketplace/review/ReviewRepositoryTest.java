package ru.sablin.app.marketplace.review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql({"/drop-schema.sql", "/test-schema.sql"})
@Sql(scripts = "/test-data.sql")
class ReviewRepositoryTest {

    @Autowired
    ReviewRepositoryImpl repository;

    @Test
    void leaveFeedback() {
        var review = new Review();
        review.setId(88);
        review.setClientName("Sem");
        review.setStoreName("DNS");
        review.setMessage("некачественный товар не советую");
        review.setRating(0);

        var expectedReview = repository.leaveFeedback(review);

        assertNotNull(expectedReview);
        assertEquals(expectedReview, review);
    }

    @Test
    void find10LastReview() {
        var expectedList = repository.find10LastReview("DNS");

        assertNotNull(expectedList);
        assertEquals(expectedList.size(), 10);
    }
}