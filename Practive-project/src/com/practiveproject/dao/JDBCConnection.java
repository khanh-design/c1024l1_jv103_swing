/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.practiveproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Admin
 */
public class JDBCConnection {
        public static Connection getConnection() {
            String url = "jdbc:mysql://localhost:3306/ban_hang";
            String user = "root";
            String password = "123456";
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try {
                    return DriverManager.getConnection(url, user, password);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    public static void main(String[] args) {
        Connection con = getConnection();
        if (con != null) {
            System.out.println("Thanh cong");
        } else {
            System.out.println("khong thanh cong");
        }
    }
}
