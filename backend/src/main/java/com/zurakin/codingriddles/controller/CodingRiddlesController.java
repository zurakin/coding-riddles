package com.zurakin.codingriddles.controller;

import com.zurakin.codingriddles.models.Riddle;
import com.zurakin.codingriddles.service.RiddlesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


//@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/riddles")
public class CodingRiddlesController {
    private final RiddlesService service;

    public CodingRiddlesController(RiddlesService service) {
        this.service = service;
    }

    @GetMapping
    public List<Riddle> getRiddles() {
        return service.getAllRiddles();
    }

    @PostMapping
    public ResponseEntity<Riddle> addRiddle(@RequestBody Riddle riddle) {
        if (riddle == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(service.addRiddle(riddle));
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