package com.example.demo.controller;

import com.example.demo.model.BlogSettings;
import com.example.demo.model.Post;
import com.example.demo.service.BlogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("stats", blogService.getBlogStats(auth.getName()));
        return "blog/dashboard";
    }

    @GetMapping("/posts")
    public String posts(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("posts", blogService.getUserPosts(auth.getName()));
        return "blog/posts";
    }

    @GetMapping("/new")
    public String newPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "blog/new-post";
    }

    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute("post") Post post,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "blog/new-post";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        blogService.createPost(post, auth.getName());
        redirectAttributes.addFlashAttribute("success", "Post created successfully!");
        return "redirect:/blog/posts";
    }

    @GetMapping("/settings")
    public String settingsForm(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("settings", blogService.getSettings(auth.getName()));
        return "blog/settings";
    }

    @PostMapping("/settings")
    public String updateSettings(@Valid @ModelAttribute("settings") BlogSettings settings,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "blog/settings";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        blogService.updateSettings(settings, auth.getName());
        redirectAttributes.addFlashAttribute("success", "Settings updated successfully!");
        return "redirect:/blog/settings";
    }

    @GetMapping("/posts/{id}/edit")
    public String editPostForm(@PathVariable Long id, Model model) {
        Post post = blogService.getPostById(id);
        model.addAttribute("post", post);
        return "blog/edit-post";
    }

    @PostMapping("/posts/{id}/edit")
    public String updatePost(@PathVariable Long id,
            @Valid @ModelAttribute("post") Post post,
            BindingResult result,
            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "blog/edit-post";
        }
        blogService.updatePost(id, post);
        redirectAttributes.addFlashAttribute("success", "Post updated successfully!");
        return "redirect:/blog/posts";
    }

    @PostMapping("/posts/{id}/delete")
    public String deletePost(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        blogService.deletePost(id);
        redirectAttributes.addFlashAttribute("success", "Post deleted successfully!");
        return "redirect:/blog/posts";
    }

    @GetMapping("/posts/{id}")
    public String viewPost(@PathVariable Long id, Model model) {
        // Lấy bài viết từ blogService theo ID
        Post post = blogService.getPostById(id);
        model.addAttribute("post", post); // Thêm bài viết vào model
        return "blog/view-post"; // Trả về view chi tiết bài viết
    }

}
