package com.zurakin.codingriddles.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name="riddles")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RiddleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;
    @Lob
    private String code;
    @Lob
    private String validationCode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "riddle")
    @Setter
    private List<TestCaseEntity> testCases;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @Setter
    private UserEntity author;
}
