package ru.sablin.app.marketplace.store;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sablin.app.marketplace.exception.StoreNotFoundException;

@RequiredArgsConstructor
@Repository
public class StoreRepositorySqlImpl implements StoreRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Store create(Store store) {
        jdbcTemplate.update("INSERT INTO Store VALUES (?,?)",
                                store.getId(),
                                store.getName());

        return jdbcTemplate.queryForObject("SELECT * FROM Store WHERE id = ?",
                new BeanPropertyRowMapper<>(Store.class), store.getId());
    }

    @Override
    public Store findById(Integer id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Store WHERE id = ?",
                    new BeanPropertyRowMapper<>(Store.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw new StoreNotFoundException(String.format("No Store by id = %s", id));
        }
    }
}