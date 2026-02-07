package com.company.approval.controller;

import com.company.approval.dto.LoginRequestDto;
import com.company.approval.entity.User;
import com.company.approval.repository.UserRepository;
import com.company.approval.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (user == null || !user.getPassword().equals(dto.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of(
                            "message", "Invalid credentials",
                            "status", 401
                    ));
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "role", user.getRole().name()
                )
        );
    }
}
