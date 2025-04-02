package com.example.demo.repository;

import com.example.demo.model.BlogSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface BlogSettingsRepository extends JpaRepository<BlogSettings, Long> {
    Optional<BlogSettings> findByUsername(String username);
}
