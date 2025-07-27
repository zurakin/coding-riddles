package com.zurakin.codingriddles.controller;

import com.zurakin.codingriddles.models.entity.RiddleEntity;
import com.zurakin.codingriddles.repository.RiddlesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RiddlesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RiddlesRepository riddlesRepository;

    @BeforeEach
    void setup() {
        riddlesRepository.deleteAll();
        RiddleEntity riddleEntity = new RiddleEntity(
                null, // let JPA generate ID
                "Reverse a String",
                "Write a function to reverse a string.",
                "function reverseString(s) {\n\treturn \"\";\n}",
                "return reverseString(input);",
                null, 
                null,
                LocalDateTime.of(2001, 1, 4, 12, 0, 0),
                Collections.emptySet()
        );
        riddlesRepository.save(riddleEntity);
    }

    @Test
    void shouldReturnRiddlesDtoFieldsOnly() throws Exception {
        mockMvc.perform(get("/api/riddles")
                        .accept(MediaType.APPLICATION_JSON))
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
