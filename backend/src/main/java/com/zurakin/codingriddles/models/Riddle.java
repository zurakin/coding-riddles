package com.zurakin.codingriddles.models;

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
public class Riddle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String description;
    private String code;
    private String validationCode;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "riddle_id")
    private List<TestCase> testCases;
}
