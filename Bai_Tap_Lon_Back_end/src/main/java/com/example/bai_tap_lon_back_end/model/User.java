package com.example.bai_tap_lon_back_end.model;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;
    @NotNull
    private String fullname;
    @NotNull
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String password;
    private LocalDateTime created;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Roles> roles;

}
