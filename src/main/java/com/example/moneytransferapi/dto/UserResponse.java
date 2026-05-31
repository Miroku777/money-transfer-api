package com.example.moneytransferapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserResponse {
    @Schema(description = "User ID", example = "1")
    private Long id;
    @Schema(description = "User name", example = "user")
    private String name;
    @Schema(description = "Date of birth", example = "2026-05-30")
    private LocalDate dateOfBirth;
    @Schema(description = "List of emails", example = "test@mail.ru")
    private List<String> emails;
    @Schema(description = "List of phones", example = "79207865432")
    private List<String> phones;
}