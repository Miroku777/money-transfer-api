package com.example.moneytransferapi.controllers;

import com.example.moneytransferapi.dto.UserResponse;
import com.example.moneytransferapi.services.UserService;
import com.example.moneytransferapi.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/search")
    @Operation(summary = "Search users", description = "Search users with filters and pagination")
    public Page<UserResponse> searchUsers(@RequestParam(required = false)
                                              @Parameter(example = "u") String name,
                                          @RequestParam(required = false)
                                          @Parameter(example = "test@mail.ru") String email,
                                          @RequestParam(required = false)
                                              @Parameter(example = "79207865432") String phone,
                                          @RequestParam(required = false)
                                              @DateTimeFormat(pattern = "yyyy-MM-dd")
                                              @Parameter(example = "2026-05-30") LocalDate dateOfBirth,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return userService.searchUsers(name, email, phone, dateOfBirth, page, size);
    }

    @PostMapping("/emails")
    @Operation(summary = "Add email", description = "Add new email to current user")
    public void addEmail(@RequestParam String email) {
        Long userId = SecurityUtils.getCurrentUserId();
        userService.addEmail(userId, email);
    }

    @PutMapping("/emails")
    @Operation(summary = "Update email", description = "Update the existing email address of the current user by email id")
    public void updateEmail(@RequestParam Long emailId,
                            @RequestParam String newEmail) {
        Long userId = SecurityUtils.getCurrentUserId();
        userService.updateEmail(userId, emailId, newEmail);
    }

    @DeleteMapping("/emails/{emailId}")
    @Operation(summary = "Delete email", description = "Delete email from current user")
    public void deleteEmail(@PathVariable Long emailId) {
        Long userId = SecurityUtils.getCurrentUserId();
        userService.deleteEmail(userId, emailId);
    }

    @PostMapping("/phones")
    @Operation(summary = "Add phone", description = "Add new phone number to current user")
    public void addPhone(@RequestParam String phone) {
        Long userId = SecurityUtils.getCurrentUserId();
        userService.addPhone(userId, phone);
    }

    @PutMapping("/phones")
    @Operation(summary = "Update phone", description = "Update existing phone of current user")
    public void updatePhone(@RequestParam Long phoneId,
                            @RequestParam String newPhone) {
        Long userId = SecurityUtils.getCurrentUserId();
        userService.updatePhone(userId, phoneId, newPhone);
    }

    @DeleteMapping("/phones/{phoneId}")
    @Operation(summary = "Delete phone", description = "Delete phone from current user")
    public void deletePhone(@PathVariable Long phoneId) {
        Long userId = SecurityUtils.getCurrentUserId();
        userService.deletePhone(userId, phoneId);
    }
}