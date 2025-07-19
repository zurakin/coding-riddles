package com.zurakin.codingriddles.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class RiddleDto {
    private Long id;

    private String title;

    private String description;
    private String code;
    private String validationCode;

    private List<TestCaseDto> testCases;
    private UserProfileDto author;
}
