package com.zurakin.codingriddles.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolvedRiddleDto {
    private Long riddleId;
    private String answerCode;
    private String solvedAt;
}
