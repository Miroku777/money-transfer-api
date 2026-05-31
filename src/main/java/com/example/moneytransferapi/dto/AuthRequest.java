package com.example.moneytransferapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    @Schema(example = "test@mail.ru")
    private String login;
    @Schema(example = "password")
    private String password;
}