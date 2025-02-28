package com.zurakin.codingriddles.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="test_cases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String input;
    private String output;

    @ManyToOne
    @JoinColumn(name = "riddle_id")
    @JsonIgnore
    private Riddle riddle;
}
