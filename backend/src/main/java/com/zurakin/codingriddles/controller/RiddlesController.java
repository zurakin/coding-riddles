package com.zurakin.codingriddles.controller;

import com.zurakin.codingriddles.models.Riddle;
import com.zurakin.codingriddles.models.TestCase;
import com.zurakin.codingriddles.models.dto.RiddleDTO;
import com.zurakin.codingriddles.models.dto.RiddleRequestDTO;
import com.zurakin.codingriddles.service.RiddlesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/riddles")
public class RiddlesController {
    private final RiddlesService service;

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

    @GetMapping(value = "/")
    public String index() {
        return "forward:/index.html";
    }

    @DeleteMapping("/{id}")
    public void deleteRiddle(@PathVariable Long id) {
        service.deleteRiddle(id);
    }
}