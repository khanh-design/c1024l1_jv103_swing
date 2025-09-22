/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bai_tap_lon.service;

/**
 *
 * @author Admin
 */
public interface IAccountService<T> {
    boolean login(String username, String password);
    void register(T user);
}
