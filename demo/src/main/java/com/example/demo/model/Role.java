package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Thuộc tính name lưu tên vai trò

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        // In name ra để kiểm tra
        System.out.println(">>> getAuthority() called, name = " + name);
        return "ROLE_" + name;
    }
}
