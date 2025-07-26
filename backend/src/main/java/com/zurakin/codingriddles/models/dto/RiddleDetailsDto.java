package com.zurakin.codingriddles.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RiddleDetailsDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
