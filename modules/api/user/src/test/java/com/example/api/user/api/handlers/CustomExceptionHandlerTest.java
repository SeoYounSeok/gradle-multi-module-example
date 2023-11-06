package com.example.api.user.api.handlers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.user.exceptions.DataDuplicationException;
import com.example.api.user.exceptions.DataNotFoundException;
import com.example.api.user.exceptions.PasswordNotMatchException;

public class CustomExceptionHandlerTest {

    private MockController mockController = mock(MockController.class);

    private MockMvc mockMvc;


    @BeforeEach
    void beforeEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(mockController)
            .setControllerAdvice(new CustomExceptionHandler())
            .build();
    }

    @RestController
    public class MockController {
        @GetMapping("/throwException")
        public String throwException() throws Exception {
            throw new Exception("This is a test exception");
        }
    }

    @Test
    void test_DataNotFoundException_handler() throws Exception {
        String throwMsg = "DataNotFoundException !";
        when(mockController.throwException()).thenThrow(new DataNotFoundException(throwMsg));

        mockMvc.perform(get("/throwException"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404 NOT-FOUND"))
                .andExpect(jsonPath("$.message").value(throwMsg));
    }

    @Test
    void test_DataDuplicationException_handler() throws Exception {
        String throwMsg = "DataDuplicationException !";
        when(mockController.throwException()).thenThrow(new DataDuplicationException(throwMsg));

        mockMvc.perform(get("/throwException"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("409 CONFLICT"))
                .andExpect(jsonPath("$.message").value(throwMsg));
    }

    @Test
    void test_PasswordNotMatchException_handler() throws Exception {
        String throwMsg = "PasswordNotMatchException !";
        when(mockController.throwException()).thenThrow(new PasswordNotMatchException(throwMsg));

        mockMvc.perform(get("/throwException"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("401 UNAUTHORIZED"))
                .andExpect(jsonPath("$.message").value(throwMsg));
    }

}
