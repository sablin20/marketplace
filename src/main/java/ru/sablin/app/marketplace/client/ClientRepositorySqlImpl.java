package ru.sablin.app.marketplace.client;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sablin.app.marketplace.exception.ClientNotFoundException;

@Repository
@RequiredArgsConstructor
public class ClientRepositorySqlImpl implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Client create(Client client) {
        jdbcTemplate.update("INSERT INTO Client VALUES (?,?,?);",
                client.getId(),
                client.getName(),
                client.getLastName());
        return jdbcTemplate.queryForObject("SELECT * FROM Client WHERE id = ?;",
               new BeanPropertyRowMapper<>(Client.class), client.getId());
    }

    @Override
    public Client findById(Integer id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM Client WHERE id = ?",
                    new BeanPropertyRowMapper<>(Client.class), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ClientNotFoundException(String.format("Client with id = %d not found", id));
        }
    }

    @Override
    public void removeById(Integer id) {
        jdbcTemplate.update("DELETE FROM Client WHERE id = ?", id);
    }
}