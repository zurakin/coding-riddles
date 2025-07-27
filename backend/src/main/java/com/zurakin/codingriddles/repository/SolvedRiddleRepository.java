package com.zurakin.codingriddles.repository;

import com.zurakin.codingriddles.models.entity.SolvedRiddleEntity;
import com.zurakin.codingriddles.models.entity.SolvedRiddleEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolvedRiddleRepository extends JpaRepository<SolvedRiddleEntity, SolvedRiddleEntityId> {
    List<SolvedRiddleEntity> findByUserId(Long userId);
    List<SolvedRiddleEntity> findByRiddleId(Long riddleId);
}
