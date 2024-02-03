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

        var expectedStore = repository.create(store);

        assertNotNull(expectedStore);
        assertEquals(expectedStore, store);
    }

    @Test
    void findById() {
        var expectedStore = new Store();
        expectedStore.setId(1);
        expectedStore.setName("DNS");

        var result = repository.findById(expectedStore.getId());

        assertNotNull(result);
        assertEquals(result, expectedStore);
    }
}