package com.zurakin.codingriddles.models.dto;

import lombok.Data;
import java.util.List;
import java.time.LocalDateTime;

@Data
public class RiddleDto {
    private Long id;
    private String title;
    private String description;
    private String code;
    private String validationCode;
    private List<TestCaseDto> testCases;
    private UserProfileDto author;
    private LocalDateTime createdAt;
}
