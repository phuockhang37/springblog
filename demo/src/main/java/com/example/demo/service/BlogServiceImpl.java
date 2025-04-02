package com.example.demo.service;

import com.example.demo.model.BlogSettings;
import com.example.demo.model.Post;
import com.example.demo.model.BlogStats;
import com.example.demo.repository.BlogSettingsRepository;
import com.example.demo.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BlogServiceImpl implements BlogService {

    private final PostRepository postRepository;
    private final BlogSettingsRepository blogSettingsRepository;

    public BlogServiceImpl(PostRepository postRepository,
                           BlogSettingsRepository blogSettingsRepository) {
        this.postRepository = postRepository;
        this.blogSettingsRepository = blogSettingsRepository;
    }

    @Override
    public BlogStats getBlogStats(String username) {
        long postCount = postRepository.countByAuthorUsername(username);
        long viewCount = postRepository.sumViewsByAuthorUsername(username);
        long commentCount = 0; // Would come from comment repository

        return new BlogStats(postCount, viewCount, commentCount);
    }

    @Override
    public List<Post> getUserPosts(String username) {
        return postRepository.findByAuthorUsernameOrderByCreatedAtDesc(username);
    }
    @Override
    public List<Post> getAllBlogs() {
        return postRepository.findAll();  // Giả sử bạn có phương thức findAll() trong repository để lấy tất cả bài viết
    }
    @Override
    public void createPost(Post post, String username) {
        post.setAuthorUsername(username);
        postRepository.save(post);
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
    }

    @Override
    public void updatePost(Long id, Post post) {
        Post existingPost = getPostById(id);
        existingPost.setTitle(post.getTitle());
        existingPost.setExcerpt(post.getExcerpt());
        existingPost.setContent(post.getContent());
        existingPost.setFeaturedImage(post.getFeaturedImage());
        existingPost.setPublished(post.isPublished());
        postRepository.save(existingPost);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public BlogSettings getSettings(String username) {
        return blogSettingsRepository.findByUsername(username)
                .orElseGet(() -> new BlogSettings(username));
    }

    @Override
    public void updateSettings(BlogSettings settings, String username) {
        settings.setUsername(username);
        blogSettingsRepository.save(settings);
    }
}