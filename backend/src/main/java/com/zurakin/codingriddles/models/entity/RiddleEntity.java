package com.zurakin.codingriddles.models.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="riddles")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"testCases", "author", "solutions"})
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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "riddle", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SolvedRiddleEntity> solutions = new HashSet<>();
}
