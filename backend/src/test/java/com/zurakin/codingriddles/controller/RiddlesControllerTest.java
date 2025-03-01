package com.zurakin.codingriddles.controller;

import com.zurakin.codingriddles.models.Riddle;
import com.zurakin.codingriddles.service.RiddlesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RiddlesController.class)
class RiddleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RiddlesService riddleService;

    @Test
    void shouldReturnListOfRiddlesWithOnlyDesiredFields() throws Exception {
        List<Riddle> riddles = Arrays.asList(new Riddle(1L, "Reverse a String",
                "Write a function to reverse a string.",
                "def reverse_string(s):",
                "reverse_string('hello') == 'olleh'",
                null));

        when(riddleService.getAllRiddles()).thenReturn(riddles);

        mockMvc.perform(get("/riddles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Reverse a String"))
                .andExpect(jsonPath("$[0].description").value("Write a function to reverse a string."))
                .andExpect(jsonPath("$[0].code").doesNotExist())
                .andExpect(jsonPath("$[0].validationCode").doesNotExist())
                .andExpect(jsonPath("$[0].testCases").doesNotExist());
    }
}
