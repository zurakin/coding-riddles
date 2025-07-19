package com.zurakin.codingriddles.models.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="test_cases")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TestCaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String input;
    private String output;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "riddle_id")
    private RiddleEntity riddle;
}
