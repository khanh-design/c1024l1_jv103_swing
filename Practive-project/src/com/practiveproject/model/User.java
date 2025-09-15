/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practiveproject.model;

/**
 *
 * @author Admin
 */
public class User {
    private int id;
    private String name;
    private String phone;
    private String user_name;
    private String password;
    private String role;
    private String about;
    private String favourites;

    public User() {

    }

    public User(int id, String name, String phone, String username, String password, String role, String about, String favouties) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.user_name = username;
        this.password = password;
        this.role = role;
        this.about = about;
        this.favourites = favouties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return user_name;
    }

    public void setUsername(String username) {
        this.user_name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getFavouties() {
        return favourites;
    }

    public void setFavouties(String favouties) {
        this.favourites = favouties;
    }
}
