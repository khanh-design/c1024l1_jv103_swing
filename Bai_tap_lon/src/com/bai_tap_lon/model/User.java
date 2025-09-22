/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bai_tap_lon.model;

/**
 *
 * @author Admin
 */
import java.time.LocalDateTime;
    
public class User {
    private Long id;
    private String user_name;
    private String full_name;
    private String email;
    private String phone;
    private String password;
    private LocalDateTime created_at;

    public User() {

    }

    public User(Long id, String user_name, String full_name, String email, String phone, String password, LocalDateTime created_at) {
        this.id = id;
        this.user_name = user_name;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.created_at = created_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}

