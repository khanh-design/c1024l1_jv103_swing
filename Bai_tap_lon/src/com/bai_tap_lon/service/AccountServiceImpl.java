/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bai_tap_lon.service;

import com.bai_tap_lon.configuration.JDBCConnection;
import static com.bai_tap_lon.configuration.JDBCConnection.getConnection;
import com.bai_tap_lon.model.User;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author Admin
 */
public class AccountServiceImpl implements IAccountService<User>{

    @Override
    public boolean login(String username, String password) {
        
        Connection conn = getConnection();
        
        String url = "SELECT * FROM USER WHERE username = ? and password = ?";

        try {
            PreparedStatement prep = conn.prepareStatement(url);
            prep.setString(1, username);
            prep.setString(2, password);
            ResultSet rs = prep.executeQuery();
            
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(User user) {
        Connection conn = getConnection();
        
        String url = "INSERT INTO USER(username, fullname, email, phone, password) VALUES (?,?,?,?,?)";
        
        try(PreparedStatement preparedStatement = conn.prepareStatement(url)){
            preparedStatement.setString(1, user.getUser_name());
            preparedStatement.setString(2, user.getFull_name());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhone());
            preparedStatement.setString(5, user.getPassword());
            int rs = preparedStatement.executeUpdate();
            
            if (rs != 0) {
                System.out.println("Them thanh cong");
            } else {
                System.out.println("Them that bai");
            }
        } catch (SQLException ex) {
            System.getLogger(AccountServiceImpl.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    
    
}
