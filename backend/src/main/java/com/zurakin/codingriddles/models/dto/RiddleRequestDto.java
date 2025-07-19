package com.zurakin.codingriddles.models.dto;

import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiddleRequestDto {
    private String title;
    private String description;
    private String code;
    private String validationCode;
    private List<TestCaseDto> testCases;
}