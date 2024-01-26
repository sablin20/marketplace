package ru.sablin.app.marketplace.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import ru.sablin.app.marketplace.product.ProductRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Sql({"/drop-schema.sql", "/test-schema.sql"})
@Sql(scripts = "/test-data.sql")
class ClientRepositoryTest {

    @Autowired
    ClientRepositorySqlImpl repository;

    Client factoryClient() {
        var client = new Client();
        client.setId(11);
        client.setName("John");
        client.setLastName("Weak");
        return client;
    }

    @Test
    void create() {
        repository.create(factoryClient());

        var expected_client = repository.findById(factoryClient().getId());

        assertNotNull(expected_client);
        assertEquals(expected_client, factoryClient());
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