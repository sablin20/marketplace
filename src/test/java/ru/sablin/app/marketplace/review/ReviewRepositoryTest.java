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
        review.setId(22);
        review.setClientName("Sem");
        review.setStoreId(2);
        review.setMessage("некачественный товар, не советую");
        review.setRating(0);

        var expected_review = repository.leaveFeedback(review);

        assertNotNull(expected_review);
        assertEquals(expected_review, review);
    }

    @Test
    void find10LastReview() {
        var expected_list = repository.find10LastReview();

        assertNotNull(expected_list);
        assertEquals(expected_list.size(), 10);
    }
}