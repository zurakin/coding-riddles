package com.zurakin.codingriddles.service;

import com.zurakin.codingriddles.models.Riddle;
import com.zurakin.codingriddles.repository.RiddlesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiddlesService {
    private final RiddlesRepository repository;

    public RiddlesService(RiddlesRepository repository) {
        this.repository = repository;
    }

    public List<Riddle> getAllRiddles() {
        return repository.findAll();
    }

    public Riddle saveRiddle(Riddle riddle) {
        return repository.save(riddle);
    }

    public void deleteRiddle(Long id) {
        repository.deleteById(id);
    }

    public Riddle getRiddleById(Long id) {
        return repository.findById(id).orElse(null);
    }
}