package com.zurakin.codingriddles.controller;

import com.zurakin.codingriddles.models.Riddle;
import com.zurakin.codingriddles.models.TestCase;
import com.zurakin.codingriddles.models.dto.RiddleDTO;
import com.zurakin.codingriddles.models.dto.RiddleRequestDTO;
import com.zurakin.codingriddles.service.RiddlesService;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/riddles")
public class RiddlesController {
    private final RiddlesService service;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(RiddlesController.class);

    public RiddlesController(RiddlesService service) {
        this.service = service;
    }

    @GetMapping
    public List<RiddleDTO> listRiddles() {
        return service.getAllRiddles().stream()
                .map(riddle -> new RiddleDTO(riddle.getId(), riddle.getTitle(), riddle.getDescription()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Riddle getRiddleById(@PathVariable Long id) {
        return service.getRiddleById(id);
    }


    @PostMapping
    public ResponseEntity<Riddle> createRiddle(@RequestBody RiddleRequestDTO riddleDTO) {
        if (riddleDTO == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Riddle riddle = new Riddle();
        riddle.setTitle(riddleDTO.getTitle());
        riddle.setDescription(riddleDTO.getDescription());
        riddle.setCode(riddleDTO.getCode());
        riddle.setValidationCode(riddleDTO.getValidationCode());

        List<TestCase> testCases = riddleDTO.getTestCases().stream()
                .map(dto -> new TestCase(null, dto.getInput(), dto.getOutput(), riddle))
                .collect(Collectors.toList());

        riddle.setTestCases(testCases);

        Riddle savedRiddle = service.saveRiddle(riddle);

        return ResponseEntity.ok(savedRiddle);
    }

    @DeleteMapping("/{id}")
    public void deleteRiddle(@PathVariable Long id, Authentication auth)
    {
        logger.info("User: {} deleted Riddle: {}", auth.getName(), id);
        service.deleteRiddle(id);
    }
}