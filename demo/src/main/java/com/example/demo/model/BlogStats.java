package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlogStats {
    private long postCount;
    private long viewCount;
    private long commentCount;
}