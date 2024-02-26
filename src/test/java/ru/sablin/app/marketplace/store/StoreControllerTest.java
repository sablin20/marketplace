package ru.sablin.app.marketplace.store;

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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StoreController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StoreControllerTest {

    @MockBean
    private StoreRepository repository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getById() throws Exception {
        var storeInDb = new Store();
        storeInDb.setId(1);
        storeInDb.setName("DNS");

        when(repository.findById(1)).thenReturn(storeInDb);

        mockMvc.perform(get("/stores/")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(storeInDb));

        verify(repository, times(1)).findById(storeInDb.getId());
    }

    @Test
    void create() throws Exception {
        var store = new Store();
        store.setId(77);
        store.setName("Amazon");

        when(repository.create(store)).thenReturn(store);

        mockMvc.perform(post("/stores/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(store)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(store));

        verify(repository, times(1)).create(store);
    }
}