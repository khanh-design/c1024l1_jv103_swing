package com.practiveproject.service;

import com.practiveproject.dao.UserDAO;
import com.practiveproject.model.User;

import java.util.List;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        userDAO = new UserDAO();
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }
}
