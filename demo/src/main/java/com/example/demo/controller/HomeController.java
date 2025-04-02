package com.example.demo.controller;

import java.util.List;
import com.example.demo.model.Post;
import com.example.demo.service.BlogService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final BlogService blogService; // Thêm blogService

    public HomeController(BlogService blogService) {
        this.blogService = blogService; // Inject BlogService
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        // Lấy thông tin người dùng hiện tại
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());

        // Kiểm tra xem người dùng có phải là admin không
        model.addAttribute("isAdmin", auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")));

        // Lấy danh sách blog từ service (giả sử blogService tồn tại)
        List<Post> blogs = blogService.getAllBlogs();
        model.addAttribute("blogs", blogs);

        return "home"; // Trả về view "home"
    }

    @GetMapping("/admin")
    public String adminPage(Model model) {
        // Lấy thông tin Authentication (người dùng hiện tại)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Ghi log tất cả các quyền của người dùng
        System.out.println("User Roles: ");
        auth.getAuthorities().forEach(authority -> {
            System.out.println("Authority: " + authority.getAuthority());
        });

        // Kiểm tra xem người dùng có quyền chứa "ADMIN" không (bất kỳ vai trò nào có
        // 'ADMIN' trong tên)
        if (auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().contains("ADMIN"))) {
            model.addAttribute("message", "Welcome to Admin Page");
            return "dashboard"; // Trả về trang dashboard nếu người dùng có quyền chứa "ADMIN"
        } else {
            return "redirect:/home"; // Nếu không có quyền chứa "ADMIN", chuyển hướng về home
        }
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}
