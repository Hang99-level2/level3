package com.sparta.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false,unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name = "departement",nullable = false)
    private String department;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AdminRoleEnum role;


    public Admin(String email, String password, String department, AdminRoleEnum role) {
        this.email = email;
        this.password = password;
        this.department = department;
        this.role = role;
    }
}
