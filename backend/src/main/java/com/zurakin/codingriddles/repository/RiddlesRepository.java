package com.zurakin.codingriddles.repository;

import com.zurakin.codingriddles.models.entity.RiddleEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RiddlesRepository extends JpaRepository<RiddleEntity, Long> {

    @EntityGraph(attributePaths = "testCases")
    Optional<RiddleEntity> findWithTestCasesById(Long id);
}