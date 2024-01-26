package ru.sablin.app.marketplace.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Sql({"/drop-schema.sql", "/test-schema.sql"})
@Sql(scripts = "/test-data.sql")
class StoreRepositoryTest {

    @Autowired
    StoreRepositorySqlImpl repository;

    @Test
    void create() {
        var store = new Store();
        store.setId(22);
        store.setName("Amazon");

        var expected_store = repository.create(store);

        assertNotNull(expected_store);
        assertEquals(expected_store, store);
    }

    @Test
    void findById() {
        var expected_store = new Store();
        expected_store.setId(1);
        expected_store.setName("DNS");

        var result = repository.findById(expected_store.getId());

        assertNotNull(result);
        assertEquals(result, expected_store);
    }
}