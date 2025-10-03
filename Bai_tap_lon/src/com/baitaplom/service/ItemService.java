package com.baitaplom.service;

import com.baitaplom.configuration.JDBCConfig;
import com.baitaplom.model.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItemService {

    public Item createItem(String name, String description, Double startPrice, Double buyNowPrice, Long sellerId, Long roomId, String images) {
        String sql = "INSERT INTO item (name, description, start_price, buy_now_price, current_price, status, created_at, seller_id, room_id, images) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            System.out.println("Creating item: " + name + " for seller: " + sellerId + " in room: " + roomId);
            
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDouble(3, startPrice);
            stmt.setDouble(4, buyNowPrice);
            stmt.setDouble(5, startPrice); 
            stmt.setString(6, "PENDING"); 
            stmt.setTimestamp(7, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            stmt.setLong(8, sellerId);
            stmt.setLong(9, roomId);
            stmt.setString(10, images);
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Long itemId = generatedKeys.getLong(1);
                        System.out.println("Created item with ID: " + itemId);
                        return new Item(itemId, name, description, startPrice, buyNowPrice, startPrice, "PENDING", LocalDateTime.now(), null, sellerId, roomId, images);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Create item error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<Item> getItemsByRoom(Long roomId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE room_id = ? ORDER BY created_at ASC";

        try (Connection conn = JDBCConfig.getConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, roomId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    java.sql.Timestamp createdTimestamp = rs.getTimestamp("created_at");
                    java.time.LocalDateTime created = null;
                    if (createdTimestamp != null) {
                        created = createdTimestamp.toLocalDateTime();
                    }
                    
                    Item item = new Item(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("start_price"),
                        rs.getDouble("buy_now_price"),
                        rs.getDouble("current_price"),
                        rs.getString("status"),
                        created,
                        rs.getString("solt_at"),
                        rs.getLong("seller_id"),
                        rs.getLong("room_id"),
                        rs.getString("images")
                    );
                    items.add(item);
                    System.out.println("Found item: " + item.getName() + " (ID: " + item.getId() + ")");
                }
            }
            System.out.println("Total items found: " + items.size());
        } catch (SQLException e) {
            System.err.println("Get items by room error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return items;
    }
    
    public Item getCurrentAuctionItem(Long roomId) {
        String sql = "SELECT * FROM item WHERE room_id = ? AND status = 'AUCTIONING' ORDER BY created_at ASC LIMIT 1";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, roomId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    java.sql.Timestamp createdTimestamp = rs.getTimestamp("created_at");
                    java.time.LocalDateTime created = null;
                    if (createdTimestamp != null) {
                        created = createdTimestamp.toLocalDateTime();
                    }
                    
                    return new Item(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("start_price"),
                        rs.getDouble("buy_now_price"),
                        rs.getDouble("current_price"),
                        rs.getString("status"),
                        created,
                        rs.getString("solt_at"),
                        rs.getLong("seller_id"),
                        rs.getLong("room_id"),
                        rs.getString("images")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Get current auction item error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public Item getNextPendingItem(Long roomId) {
        String sql = "SELECT * FROM item WHERE room_id = ? AND status = 'PENDING' ORDER BY created_at ASC LIMIT 1";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, roomId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    java.sql.Timestamp createdTimestamp = rs.getTimestamp("created_at");
                    java.time.LocalDateTime created = null;
                    if (createdTimestamp != null) {
                        created = createdTimestamp.toLocalDateTime();
                    }
                    
                    return new Item(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("start_price"),
                        rs.getDouble("buy_now_price"),
                        rs.getDouble("current_price"),
                        rs.getString("status"),
                        created,
                        rs.getString("solt_at"),
                        rs.getLong("seller_id"),
                        rs.getLong("room_id"),
                        rs.getString("images")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Get next pending item error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean startAuction(Long itemId) {
        String sql = "UPDATE item SET status = 'AUCTIONING' WHERE id = ? AND status = 'PENDING'";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, itemId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Start auction error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean endAuction(Long itemId, String status) {
        String sql = "UPDATE item SET status = ?, solt_at = ? WHERE id = ?";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            stmt.setLong(3, itemId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("End auction error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateCurrentPrice(Long itemId, Double newPrice) {
        String sql = "UPDATE item SET current_price = ? WHERE id = ?";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDouble(1, newPrice);
            stmt.setLong(2, itemId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Update current price error: " + e.getMessage());
            return false;
        }
    }
    
    public List<Item> getItemsBySeller(Long sellerId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM item WHERE seller_id = ? ORDER BY created_at DESC";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, sellerId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    java.sql.Timestamp createdTimestamp = rs.getTimestamp("created_at");
                    java.time.LocalDateTime created = null;
                    if (createdTimestamp != null) {
                        created = createdTimestamp.toLocalDateTime();
                    }
                    
                    Item item = new Item(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("start_price"),
                        rs.getDouble("buy_now_price"),
                        rs.getDouble("current_price"),
                        rs.getString("status"),
                        created,
                        rs.getString("solt_at"),
                        rs.getLong("seller_id"),
                        rs.getLong("room_id"),
                        rs.getString("images")
                    );
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            System.err.println("Get items by seller error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return items;
    }
}