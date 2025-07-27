package com.zurakin.codingriddles.models.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SolveRiddleRequestDto {
    private Long riddleId;
    private String answerCode;
}

