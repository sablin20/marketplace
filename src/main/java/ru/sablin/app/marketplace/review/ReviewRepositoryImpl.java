package ru.sablin.app.marketplace.review;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Review leaveFeedback(Review review) {
        jdbcTemplate.update("insert into Review values (?, ?, ?, ?, ?)",
                review.getId(),
                review.getClientName(),
                review.getStoreName(),
                review.getMessage(),
                review.getRating());

        return jdbcTemplate.queryForObject("select * from Review where id = ?",
                new BeanPropertyRowMapper<>(Review.class), review.getId());
    }

    @Override
    public List<Review> find10LastReview(String storeName) {
        return jdbcTemplate.query("select * from Review where store_name = ? limit 10",
                new BeanPropertyRowMapper<>(Review.class), storeName);
    }
}
