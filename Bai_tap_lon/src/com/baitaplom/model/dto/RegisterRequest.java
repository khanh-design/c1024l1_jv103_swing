/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baitaplom.model.dto;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class RegisterRequest implements Serializable {
    
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private String password;
    
    public RegisterRequest() {}
    
    public RegisterRequest(String username, String fullname, String email, String phone, String password) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFullname() {
        return fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
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
    
    @Override
    public String toString() {
        return "RegisterRequest{" + "username=" + username + ", fullname=" + fullname + ", email=" + email + ", phone=" + phone + ", password=" + password + '}';
    }
}