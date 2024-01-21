package ru.sablin.app.marketplace.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    ClientRepository repository;

    @InjectMocks
    private ClientController controller;

    @Mock
    Client clientMock;

    @BeforeEach
    void factoryClient() {
        clientMock = new Client();
        clientMock.setId(1);
        clientMock.setName("John");
        clientMock.setLastName("Smith");
    }

    @Test
    void create() {
        when(repository.create(clientMock)).thenReturn(clientMock);

        var result = controller.create(clientMock);

        assertEquals(result, clientMock);
        verify(repository).create(clientMock);
    }

    @Test
    void remove() {
        controller.remove(clientMock.getId());

        assertNull(controller.getById(clientMock.getId()));
        verify(repository).removeById(clientMock.getId());
    }

    @Test
    void getById() {
        when(repository.findById(clientMock.getId())).thenReturn(clientMock);

        var result = controller.getById(clientMock.getId());

        assertEquals(result.getName(), clientMock.getName());
        assertEquals(result.getLastName(), clientMock.getLastName());
        assertEquals(result, clientMock);
        verify(repository).findById(clientMock.getId());
    }
}