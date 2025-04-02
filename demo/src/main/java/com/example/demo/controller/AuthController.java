package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login"; // Spring Security will handle login
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) boolean isAdmin,
            Model model) {

        try {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            userService.registerUser(user, isAdmin);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        // Spring Security tự động xử lý logout, bạn có thể thêm thông báo sau khi
        // logout thành công
        model.addAttribute("message", "You have successfully logged out.");
        return "login"; // Redirect về trang login
    }
}
