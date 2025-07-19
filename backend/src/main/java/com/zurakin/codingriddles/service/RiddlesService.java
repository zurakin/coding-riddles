package com.zurakin.codingriddles.service;

import com.zurakin.codingriddles.models.entity.RiddleEntity;
import com.zurakin.codingriddles.repository.RiddlesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiddlesService {
    public static class ForbiddenException extends RuntimeException {
        public ForbiddenException(String message) { super(message); }
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) { super(message); }
    }

    public void deleteRiddleAuthorized(Long id, String currentUsername) {
        RiddleEntity riddle = getRiddleByIdWithTestCases(id);
        if (riddle == null) {
            throw new NotFoundException("Riddle not found");
        }
        String authorUsername = riddle.getAuthor() != null ? riddle.getAuthor().getUsername() : null;
        if (currentUsername == null || !currentUsername.equals(authorUsername)) {
            throw new ForbiddenException("You are not allowed to delete this riddle");
        }
        deleteRiddle(id);
    }
    private final RiddlesRepository repository;

    public RiddlesService(RiddlesRepository repository) {
        this.repository = repository;
    }

    public List<RiddleEntity> getAllRiddles() {
        return repository.findAll();
    }

    public RiddleEntity saveRiddle(RiddleEntity riddleEntity) {
        if (riddleEntity.getTestCases() != null) {
            riddleEntity.getTestCases().forEach(testCase -> testCase.setRiddle(riddleEntity));
        }

        return repository.save(riddleEntity);
    }

    public void deleteRiddle(Long id) {
        repository.deleteById(id);
    }

    public RiddleEntity getRiddleById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public RiddleEntity getRiddleByIdWithTestCases(Long id) {
        return repository.findWithTestCasesById(id).orElse(null);
    }
}