package com.zurakin.codingriddles.service;

import com.zurakin.codingriddles.models.Riddle;
import com.zurakin.codingriddles.repository.RiddlesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiddlesServiceTest {

    @Mock
    private RiddlesRepository riddleRepository;

    @InjectMocks
    private RiddlesService riddleService;

    private Riddle riddle;

    @BeforeEach
    void setUp() {
        riddle = new Riddle(1L, "Reverse a String", "Write a function to reverse a string.", "def reverse_string(s):", "reverse_string('hello') == 'olleh'", null);
    }

    @Test
    void shouldReturnAllRiddles() {
        when(riddleRepository.findAll()).thenReturn(Arrays.asList(riddle));

        List<Riddle> riddles = riddleService.getAllRiddles();

        assertEquals(1, riddles.size());
        assertEquals("Reverse a String", riddles.get(0).getTitle());
    }

    @Test
    void shouldReturnRiddleById() {
        when(riddleRepository.findById(1L)).thenReturn(Optional.of(riddle));

        Riddle foundRiddle = riddleService.getRiddleById(1L);

        assertNotNull(foundRiddle);
        assertEquals("Reverse a String", foundRiddle.getTitle());
    }

    @Test
    void shouldSaveRiddle() {
        when(riddleRepository.save(any(Riddle.class))).thenReturn(riddle);

        Riddle savedRiddle = riddleService.saveRiddle(riddle);

        assertNotNull(savedRiddle);
        assertEquals("Reverse a String", savedRiddle.getTitle());
    }
}
