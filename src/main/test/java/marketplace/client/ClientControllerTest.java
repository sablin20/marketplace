package marketplace.client;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
class ClientControllerTest {

    @Autowired
    ClientController controller;

    @Mock
    ClientRepository clientRepositorySql;

    @Test
    void m() {
//        final Client client = mock(Client.class);
//        client.setName("Alex");
//        client.setId(999);
//        client.setLastName("Fil");
////        Mockito.doReturn(client).when(clientRepositorySql.findById(999));
//
//        Mockito.when(clientRepositorySql.findById(999)).thenReturn(client);
//        var clientTest = controller.getById(999);
//
//        assertEquals(clientTest.getLastName(), client.getLastName());

    }
}