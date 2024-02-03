package ru.sablin.app.marketplace.review;

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
import ru.sablin.app.marketplace.client.ClientController;
import ru.sablin.app.marketplace.exception.ReviewRatingNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = ReviewController.class)
class ReviewControllerTest {

    @MockBean
    ReviewService service;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void leaveFeedback() throws Exception {
        var review = new Review();
        review.setId(1);
        review.setClientName("Sem");
        review.setStoreName("DNS");
        review.setMessage("некачественный товар не советую");
        review.setRating(0);

        when(service.leaveFeedback(review)).thenReturn(review);

        mockMvc.perform(post("/review/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(review));

        verify(service, times(1)).leaveFeedback(review);
    }

    @Test
    void getAvgRatingLast10ReviewAndTop3WordBeforeProduct() throws Exception {
        mockMvc.perform(get("/review/")
                        .param("storeName", "DNS"))
                .andExpect(status().isOk());
    }
}