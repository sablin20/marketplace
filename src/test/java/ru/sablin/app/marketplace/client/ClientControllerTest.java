package ru.sablin.app.marketplace.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {

    @MockBean
    ClientRepository repository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;

    Client factoryClient() {
        var clientMock = new Client();
        clientMock.setId(87);
        clientMock.setName("John");
        clientMock.setLastName("Smith");
        return clientMock;
    }

    @Test
    void create() throws Exception {
        when(repository.create(factoryClient())).thenReturn(factoryClient());

        mockMvc.perform(post("/client/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factoryClient())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(factoryClient()));

        verify(repository).create(factoryClient());
    }

    @Test
    void remove() throws Exception {
        mockMvc.perform(delete("/client/{id}", factoryClient().getId()))
                        .andExpect(status().isOk());

        verify(repository, times(1)).removeById(factoryClient().getId());
    }

    @Test
    void getById() throws Exception {
        when(repository.findById(factoryClient().getId())).thenReturn(factoryClient());

        mockMvc.perform(get("/client/")
                        .param("id", "87"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(factoryClient()));

        verify(repository, times(1)).findById(factoryClient().getId());
    }
}