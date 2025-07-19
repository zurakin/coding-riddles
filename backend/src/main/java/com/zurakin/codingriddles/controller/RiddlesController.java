package com.zurakin.codingriddles.controller;

import com.zurakin.codingriddles.models.domain.User;
import com.zurakin.codingriddles.models.dto.RiddleDetailsDto;
import com.zurakin.codingriddles.models.dto.RiddleDto;
import com.zurakin.codingriddles.models.dto.RiddleRequestDto;
import com.zurakin.codingriddles.models.entity.RiddleEntity;
import com.zurakin.codingriddles.models.mapper.RiddleMapper;
import com.zurakin.codingriddles.models.mapper.UserMapper;
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
    private final RiddleMapper riddleMapper = RiddleMapper.INSTANCE;

    private final UserMapper userMapper = UserMapper.INSTANCE;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(RiddlesController.class);

    public RiddlesController(RiddlesService service) {
        this.service = service;
    }

    @GetMapping
    public List<RiddleDetailsDto> listRiddles() {
        return service.getAllRiddles().stream()
                .map(riddleMapper::toDetailsDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RiddleDto> getRiddleById(@PathVariable Long id) {
        RiddleEntity entity = service.getRiddleByIdWithTestCases(id);
        if (entity == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.ok(riddleMapper.toDto(entity));
    }

    @PostMapping
    public ResponseEntity<RiddleDto> createRiddle(@RequestBody RiddleRequestDto riddleDTO, Authentication auth) {
        if (riddleDTO == null) {
            return ResponseEntity.badRequest().body(null);
        }
        RiddleEntity riddleEntity = riddleMapper.toEntity(riddleDTO);
        if (auth != null && auth.getPrincipal() instanceof User user) {
            riddleEntity.setAuthor(userMapper.toEntity(user));
        }
        RiddleEntity savedRiddleEntity = service.saveRiddle(riddleEntity);
        RiddleDto riddleDto = riddleMapper.toDto(savedRiddleEntity);
        return ResponseEntity.ok(riddleDto);
    }

    @DeleteMapping("/{id}")
    public void deleteRiddle(@PathVariable Long id, Authentication auth) {
        String currentUsername = auth != null ? auth.getName() : null;
        try {
            service.deleteRiddleAuthorized(id, currentUsername);
            logger.info("User: {} deleted Riddle: {}", currentUsername, id);
        } catch (RiddlesService.ForbiddenException e) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.FORBIDDEN, e.getMessage());
        } catch (RiddlesService.NotFoundException e) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}