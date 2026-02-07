package com.company.approval.controller;

import com.company.approval.dto.UserRequestDto;
import com.company.approval.entity.User;
import com.company.approval.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Create user (Admin / Initial setup)
    @PostMapping
    public User createUser(@Valid @RequestBody UserRequestDto dto) {
        return userService.createUser(dto);
    }

    // Get user by id
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
