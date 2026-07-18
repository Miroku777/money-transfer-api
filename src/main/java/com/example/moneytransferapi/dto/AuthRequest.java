package com.example.moneytransferapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthRequest {
    @NotBlank
    @Schema(example = "user@mail.ru")
    private String login;

    @NotBlank
    @Size(min = 8)
    @Schema(example = "User_password")
    private String password;
}