package com.zurakin.codingriddles.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolvedRiddleEntityId implements Serializable {
    private Long userId;
    private Long riddleId;
}

