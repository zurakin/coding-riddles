package com.zurakin.codingriddles.repository;

import com.zurakin.codingriddles.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);

    // Returns the top 10 users sorted by number of solved riddles
    @Query("SELECT u FROM UserEntity u LEFT JOIN SolvedRiddleEntity s ON u.id = s.user.id GROUP BY u.id ORDER BY COUNT(s.id) DESC")
    List<UserEntity> findTop10BySolvedRiddles();
}
