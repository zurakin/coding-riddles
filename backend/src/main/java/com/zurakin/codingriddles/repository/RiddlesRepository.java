package com.zurakin.codingriddles.repository;

import com.zurakin.codingriddles.models.Riddle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiddlesRepository extends JpaRepository<Riddle, Long> {
}