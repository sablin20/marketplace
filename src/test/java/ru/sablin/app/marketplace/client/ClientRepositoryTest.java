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

    Client createdClient() {
        var client = new Client();
        client.setId(11);
        client.setName("John");
        client.setLastName("Weak");
        return client;
    }

    @Test
    void create() {
        repository.create(createdClient());

        var expectedClient = repository.findById(createdClient().getId());

        assertNotNull(expectedClient);
        assertEquals(expectedClient, createdClient());
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