package com.example.demo.service;

import com.example.demo.model.BlogSettings;
import com.example.demo.model.Post;
import com.example.demo.model.BlogStats;

import java.util.List;

public interface BlogService {
    BlogStats getBlogStats(String username);
    List<Post> getUserPosts(String username);
    List<Post> getAllBlogs();
    void createPost(Post post, String username);
    Post getPostById(Long id);
    void updatePost(Long id, Post post);
    void deletePost(Long postId);
    BlogSettings getSettings(String username);
    void updateSettings(BlogSettings settings, String username);
}