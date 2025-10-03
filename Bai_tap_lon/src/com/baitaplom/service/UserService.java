/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baitaplom.service;

import com.baitaplom.configuration.JDBCConfig;
import com.baitaplom.model.User;
import com.baitaplom.model.dto.LoginRequest;
import com.baitaplom.model.dto.RegisterRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class UserService {
    
    public User login(LoginRequest loginRequest) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, loginRequest.getUsername());
            stmt.setString(2, loginRequest.getPassword()); 
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Xử lý trường hợp cột created có thể NULL
                    java.sql.Timestamp createdTimestamp = rs.getTimestamp("created");
                    java.time.LocalDateTime created = null;
                    if (createdTimestamp != null) {
                        created = createdTimestamp.toLocalDateTime();
                    }
                    
                    return new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        created
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Login error: " + e.getMessage());
        }
        
        return null;
    }
    
    public boolean register(RegisterRequest registerRequest) {
        // Kiểm tra username đã tồn tại chưa
        if (isUsernameExists(registerRequest.getUsername())) {
            return false;
        }
        
        // Kiểm tra email đã tồn tại chưa
        if (isEmailExists(registerRequest.getEmail())) {
            return false;
        }
        
        String sql = "INSERT INTO user (username, fullname, email, phone, password) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, registerRequest.getUsername());
            stmt.setString(2, registerRequest.getFullname());
            stmt.setString(3, registerRequest.getEmail());
            stmt.setString(4, registerRequest.getPhone());
            stmt.setString(5, registerRequest.getPassword());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Register error: " + e.getMessage());
            return false;
        }
    }
    
    private boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM user WHERE username = ?";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Check username error: " + e.getMessage());
        }
        
        return false;
    }
    
    private boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Check email error: " + e.getMessage());
        }
        
        return false;
    }
}
