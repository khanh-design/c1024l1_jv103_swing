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
    
    public void addUser(User user) {
        userDAO.addUser(user);
    }
    
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
    
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }
    
    public void updateUser(User user){
        userDAO.updateUser(user);
    }
}
