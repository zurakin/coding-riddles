package com.zurakin.codingriddles.models.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthRequest {
    private String username;
    private String password;
}