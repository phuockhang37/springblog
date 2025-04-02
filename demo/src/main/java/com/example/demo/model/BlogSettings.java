package com.example.demo.model;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
public class BlogSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String displayName;
    private String bio;
    private String blogTitle;
    private String blogDescription;
    private int postsPerPage = 10;
    private String instagramUrl;
    private String pinterestUrl;
    private String twitterUrl;

    public BlogSettings() {}

    public BlogSettings(String username) {
        this.username = username;
        this.blogTitle = username + "'s Blog";
    }
}