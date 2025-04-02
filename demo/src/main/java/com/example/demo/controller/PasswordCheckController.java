package com.example.demo.controller;

import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/password-check")
public class PasswordCheckController {
    private final UserRepository userRepository;

    public PasswordCheckController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<String> checkPasswords() {
        return userRepository.findAll().stream()
                .map(user -> user.getUsername() + ": " +
                        (user.getPassword().startsWith("$2a$") ?
                                "Encoded (BCrypt)" : "Plaintext"))
                .collect(Collectors.toList());
    }
}