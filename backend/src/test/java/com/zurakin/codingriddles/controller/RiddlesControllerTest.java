package com.zurakin.codingriddles;

import com.zurakin.codingriddles.models.Riddle;
import com.zurakin.codingriddles.repository.RiddlesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        Riddle riddle = new Riddle(
                null, // let JPA generate ID
                "Reverse a String",
                "Write a function to reverse a string.",
                "def reverse_string(s):",
                "reverse_string('hello') == 'olleh'",
                null
        );
        riddlesRepository.save(riddle);
    }

    @Test
    void shouldReturnRiddlesWithoutRestrictedFields() throws Exception {
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
