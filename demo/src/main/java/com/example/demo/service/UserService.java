package com.example.demo.service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.exception.UserAlreadyExistsException;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user, boolean isAdmin) {
        // Kiểm tra username không null hoặc trống
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        // Kiểm tra nếu username đã tồn tại
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists: " + user.getUsername());
        }

        // Gán vai trò cho user (ROLE_ADMIN hoặc ROLE_USER)
        String role = isAdmin ? "ADMIN" : "USER"; // Chỉ cần gán tên vai trò (không cần phải lấy từ bảng 'roles')

        // Mã hóa mật khẩu người dùng
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Gán vai trò vào cột 'role' của người dùng
        user.setRole(role); // Thay vì gán 'roles', trực tiếp gán vào cột role của bảng users

        // Đặt trạng thái tài khoản là enabled
        user.setEnabled(true);

        // Lưu người dùng vào cơ sở dữ liệu
        userRepository.save(user);

        // Ghi log sau khi đăng ký thành công
        logger.info("User registered successfully: {}", user.getUsername());
    }

}
