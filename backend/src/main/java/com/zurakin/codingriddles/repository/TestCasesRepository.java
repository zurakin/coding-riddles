package com.zurakin.codingriddles.repository;

import com.zurakin.codingriddles.models.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCasesRepository extends JpaRepository<TestCase, Long> {
}