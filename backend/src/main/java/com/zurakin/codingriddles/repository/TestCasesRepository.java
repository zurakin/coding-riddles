package com.zurakin.codingriddles.repository;

import com.zurakin.codingriddles.models.entity.TestCaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCasesRepository extends JpaRepository<TestCaseEntity, Long> {
}