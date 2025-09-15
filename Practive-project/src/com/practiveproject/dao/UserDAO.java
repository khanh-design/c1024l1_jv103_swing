/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practiveproject.dao;


import com.practiveproject.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;

/**
 * @author Admin
 */
public class UserDAO {
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Connection connection = JDBCConnection.getConnection();
        String sql = "SELECT * FROM USER";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                User user1 = new User();
                user1.setId(rs.getInt("id"));
                user1.setName(rs.getString("name"));
                user1.setPhone(rs.getString("phone"));
                user1.setUsername(rs.getString("user_name"));
                user1.setPassword(rs.getString("password"));
                user1.setRole(rs.getString("role"));
                user1.setAbout(rs.getString("about"));
                user1.setFavouties(rs.getString("favourites"));

                users.add(user1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void addUser(User user) {
        Connection connection = JDBCConnection.getConnection();
        String sql = "INSERT INTO USER(NAME, PHONE, USERNAME, PASSWORD, ROLE, FAVOURITES) VALUES(?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setString(6, user.getAbout());
            preparedStatement.setString(7, user.getFavouties());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUser(User user) {
        Connection connection = JDBCConnection.getConnection();
        String sql = "UPDATE User SET NAME = ?, PHONE = ?, USERNAME = ?, PASSWORD = ?, ROLE = ?, ABOUT = ?, FAVOURITES = ?, WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhone());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setString(6, user.getAbout());
            preparedStatement.setString(7, user.getFavouties());
            preparedStatement.setInt(8, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(User user) {
        Connection connection = JDBCConnection.getConnection();
        String sql = "DELETE FROM USER WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
