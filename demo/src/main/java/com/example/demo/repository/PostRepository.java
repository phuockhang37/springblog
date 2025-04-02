package com.example.demo.repository;

import com.example.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorUsernameOrderByCreatedAtDesc(String username);

    long countByAuthorUsername(String username);

    @Query("SELECT COALESCE(SUM(p.views), 0) FROM Post p WHERE p.authorUsername = :username")
    long sumViewsByAuthorUsername(String username);
}