package ru.sablin.app.marketplace.store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreControllerTest {

    @Mock
    private StoreRepository repository;

    @InjectMocks
    private StoreController controller;

    @Mock
    private Store storeMock;

    @BeforeEach
    void factoryStore() {
        storeMock = new Store();
        storeMock.setId(1);
        storeMock.setName("DNS");
    }

    @Test
    void getById() {
        when(repository.findById(storeMock.getId())).thenReturn(storeMock);

        var result = controller.getById(storeMock.getId());

        assertEquals(result.getName(), storeMock.getName());
        assertEquals(result, storeMock);
        verify(repository).findById(storeMock.getId());
    }

    @Test
    void create() {
        when(repository.create(storeMock)).thenReturn(storeMock);

        var result = controller.create(storeMock);

        assertEquals(result, storeMock);
        verify(repository).create(storeMock);
    }
}