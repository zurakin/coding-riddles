package com.zurakin.codingriddles.service;

import com.zurakin.codingriddles.models.entity.RiddleEntity;
import com.zurakin.codingriddles.models.entity.UserEntity;
import com.zurakin.codingriddles.repository.RiddlesRepository;
import com.zurakin.codingriddles.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;

    public void deleteRiddleAuthorized(Long id, String currentUsername) {
        RiddleEntity riddle = getRiddleByIdWithTestCases(id);
        if (riddle == null) {
            throw new NotFoundException("Riddle not found");
        }
        if (currentUsername == null) {
            throw new ForbiddenException("You are not allowed to delete this riddle");
        }
        // Author can delete
        if (riddle.getAuthor() != null && currentUsername.equals(riddle.getAuthor().getUsername())) {
            deleteRiddle(id);
            return;
        }
        // Check current user's role
        UserEntity currentUser = userRepository.findByUsername(currentUsername).orElse(null);
        if (currentUser != null && (currentUser.getRole() == com.zurakin.codingriddles.models.entity.RoleEntity.MODERATOR || currentUser.getRole() == com.zurakin.codingriddles.models.entity.RoleEntity.ADMIN)) {
            deleteRiddle(id);
            return;
        }
        throw new ForbiddenException("You are not allowed to delete this riddle");
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