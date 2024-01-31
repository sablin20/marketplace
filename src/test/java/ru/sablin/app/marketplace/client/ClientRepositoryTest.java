package ru.sablin.app.marketplace.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Sql({"/drop-schema.sql", "/test-schema.sql"})
@Sql(scripts = "/test-data.sql")
class ClientRepositoryTest {

    @Autowired
    ClientRepositorySqlImpl repository;

    Client created_client() {
        var client = new Client();
        client.setId(11);
        client.setName("John");
        client.setLastName("Weak");
        return client;
    }

    @Test
    void create() {
        repository.create(created_client());

        var expected_client = repository.findById(created_client().getId());

        assertNotNull(expected_client);
        assertEquals(expected_client, created_client());
    }

    @Test
    void findById() {
        var result = repository.findById(1);

        assertEquals(result.getName(), "Alex");
        assertEquals(result.getLastName(), "Smith");
    }

//    @Test
//    void removeById() {
//        repository.removeById(1);
//
//    }
}