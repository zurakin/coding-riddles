package com.zurakin.codingriddles.models.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solved_riddles")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SolvedRiddleEntity {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private SolvedRiddleEntityId id = new SolvedRiddleEntityId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("riddleId")
    private RiddleEntity riddle;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private UserEntity user;

    @Lob
    @Column(nullable = false)
    private String answerCode;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime solvedAt;
}
