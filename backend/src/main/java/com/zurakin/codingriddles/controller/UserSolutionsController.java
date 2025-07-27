package com.zurakin.codingriddles.controller;

import com.zurakin.codingriddles.models.entity.RiddleEntity;
import com.zurakin.codingriddles.models.entity.SolvedRiddleEntity;
import com.zurakin.codingriddles.models.entity.UserEntity;
import com.zurakin.codingriddles.models.dto.SolveRiddleRequestDto;
import com.zurakin.codingriddles.models.dto.SolvedRiddleDto;
import com.zurakin.codingriddles.models.mapper.SolvedRiddleMapper;
import com.zurakin.codingriddles.repository.RiddlesRepository;
import com.zurakin.codingriddles.repository.SolvedRiddleRepository;
import com.zurakin.codingriddles.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/riddle-solutions")
public class UserSolutionsController {
    @Autowired
    private SolvedRiddleRepository SolvedRiddleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RiddlesRepository riddlesRepository;

    private final SolvedRiddleMapper solvedRiddleMapper = SolvedRiddleMapper.INSTANCE;

    @GetMapping
    public ResponseEntity<List<SolvedRiddleDto>> getSolvedRiddles(Authentication auth) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(auth.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        UserEntity user = userOpt.get();
        List<SolvedRiddleEntity> solvedRiddleEntities = SolvedRiddleRepository.findByUserId(user.getId());
        List<SolvedRiddleDto> dtos = solvedRiddleEntities.stream()
            .map(solvedRiddleMapper::toDto)
            .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<SolvedRiddleDto> postSolvedRiddle(Authentication auth, @RequestBody SolveRiddleRequestDto requestDto) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(auth.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        Optional<RiddleEntity> riddleOpt = riddlesRepository.findById(requestDto.getRiddleId());
        if (riddleOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        UserEntity user = userOpt.get();
        RiddleEntity riddle = riddleOpt.get();
        // Remove previous solution if exists
        List<SolvedRiddleEntity> previous = SolvedRiddleRepository.findByUserId(user.getId()).stream()
            .filter(e -> e.getRiddle().getId().equals(riddle.getId())).toList();
        previous.forEach(SolvedRiddleRepository::delete);
        // Save new solution
        SolvedRiddleEntity solvedRiddleEntity = new SolvedRiddleEntity();
        solvedRiddleEntity.setUser(user);
        solvedRiddleEntity.setRiddle(riddle);
        solvedRiddleEntity.setAnswerCode(requestDto.getAnswerCode());
        SolvedRiddleEntity saved = SolvedRiddleRepository.save(solvedRiddleEntity);
        SolvedRiddleDto dto = solvedRiddleMapper.toDto(saved);
        return ResponseEntity.ok(dto);
    }
}
