package com.zurakin.codingriddles.service;

import com.zurakin.codingriddles.models.entity.RiddleEntity;
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

    private RiddleEntity riddleEntity;

    @BeforeEach
    void setUp() {
        riddleEntity = new RiddleEntity(1L, "Reverse a String", "Write a function to reverse a string.", "def reverse_string(s):", "reverse_string('hello') == 'olleh'", null, null);
    }

    @Test
    void shouldReturnAllRiddles() {
        when(riddleRepository.findAll()).thenReturn(Arrays.asList(riddleEntity));

        List<RiddleEntity> riddleEntities = riddleService.getAllRiddles();

        assertEquals(1, riddleEntities.size());
        assertEquals("Reverse a String", riddleEntities.get(0).getTitle());
    }

    @Test
    void shouldReturnRiddleById() {
        when(riddleRepository.findById(1L)).thenReturn(Optional.of(riddleEntity));

        RiddleEntity foundRiddleEntity = riddleService.getRiddleById(1L);

        assertNotNull(foundRiddleEntity);
        assertEquals("Reverse a String", foundRiddleEntity.getTitle());
    }

    @Test
    void shouldSaveRiddle() {
        when(riddleRepository.save(any(RiddleEntity.class))).thenReturn(riddleEntity);

        RiddleEntity savedRiddleEntity = riddleService.saveRiddle(riddleEntity);

        assertNotNull(savedRiddleEntity);
        assertEquals("Reverse a String", savedRiddleEntity.getTitle());
    }
}
