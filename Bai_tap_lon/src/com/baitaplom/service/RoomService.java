package com.baitaplom.service;

import com.baitaplom.configuration.JDBCConfig;
import com.baitaplom.model.AuctionRoom;
import com.baitaplom.model.Item;
import com.baitaplom.model.User;
import com.baitaplom.model.dto.CreateRoomRequest;
import com.baitaplom.model.dto.JoinRoomRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    
    public AuctionRoom createRoom(CreateRoomRequest request) {
        String sql = "INSERT INTO auction_room (name, status, created_at, owner_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            System.out.println("Creating room: " + request.getRoomName() + " for owner: " + request.getOwnerId());
            
            stmt.setString(1, request.getRoomName());
            stmt.setString(2, "ACTIVE");
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            stmt.setLong(4, request.getOwnerId());
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        Long roomId = generatedKeys.getLong(1);
                        System.out.println("Created room with ID: " + roomId);
                        return new AuctionRoom(roomId, request.getRoomName(), "ACTIVE", LocalDateTime.now(), request.getOwnerId());
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Create room error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean joinRoom(JoinRoomRequest request) {
        System.out.println("Attempting to join room: " + request.getRoomId() + " for user: " + request.getUserId());
        
        if (!isRoomActive(request.getRoomId())) {
            System.out.println("Room " + request.getRoomId() + " is not active");
            return false;
        }
        
        try (Connection conn = JDBCConfig.getConnection()) {
            conn.setAutoCommit(false);
            
            if (isUserInAnyRoom(request.getUserId())) {
                System.out.println("User " + request.getUserId() + " is already in a room, leaving old room first");
                leaveRoom(request.getUserId());
        }
        
        String sql = "INSERT INTO roomparticipant (user_id, room_id, joined_at, is_active) VALUES (?, ?, ?, ?)";
        
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, request.getUserId());
            stmt.setLong(2, request.getRoomId());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            stmt.setBoolean(4, true);
            
            int rowsAffected = stmt.executeUpdate();
                System.out.println("Join room SQL executed, rows affected: " + rowsAffected);
                
                conn.commit();
            return rowsAffected > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Join room error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean leaveRoom(Long userId) {
        String sql = "UPDATE roomparticipant SET is_active = false WHERE user_id = ? AND is_active = true";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, userId);
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Leave room SQL executed, rows affected: " + rowsAffected);
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Leave room error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    public List<AuctionRoom> getAllActiveRooms() {
        List<AuctionRoom> rooms = new ArrayList<>();
        String sql = "SELECT * FROM auction_room WHERE status = 'ACTIVE' ORDER BY created_at DESC";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    java.sql.Timestamp createdTimestamp = rs.getTimestamp("created_at");
                    java.time.LocalDateTime created = null;
                    if (createdTimestamp != null) {
                        created = createdTimestamp.toLocalDateTime();
                    }
                    
                    AuctionRoom room = new AuctionRoom(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("status"),
                        created,
                        rs.getLong("owner_id")
                    );
                    rooms.add(room);
                    System.out.println("Found room: " + room.getName() + " (ID: " + room.getId() + ")");
                }
            }
            System.out.println("Total rooms found: " + rooms.size());
        } catch (SQLException e) {
            System.err.println("Get rooms error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return rooms;
    }
    
    public List<User> getRoomParticipants(Long roomId) {
        List<User> participants = new ArrayList<>();
        String sql = """
            SELECT u.* FROM user u 
            INNER JOIN roomparticipant rp ON u.id = rp.user_id 
            WHERE rp.room_id = ? AND rp.is_active = true
        """;
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, roomId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    java.sql.Timestamp createdTimestamp = rs.getTimestamp("created");
                    java.time.LocalDateTime created = null;
                    if (createdTimestamp != null) {
                        created = createdTimestamp.toLocalDateTime();
                    }
                    
                    User user = new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("password"),
                        created
                    );
                    participants.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Get room participants error: " + e.getMessage());
        }
        
        return participants;
    }
    
    public boolean isUserInAnyRoom(Long userId) {
        String sql = "SELECT COUNT(*) FROM roomparticipant WHERE user_id = ? AND is_active = true";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("User " + userId + " is in " + count + " active rooms");
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Check user in room error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    private boolean isRoomActive(Long roomId) {
        String sql = "SELECT COUNT(*) FROM auction_room WHERE id = ? AND status = 'ACTIVE'";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, roomId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Room " + roomId + " active status: " + (count > 0 ? "ACTIVE" : "INACTIVE"));
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Check room active error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public String debugJoinRoom(Long userId, Long roomId) {
        StringBuilder debug = new StringBuilder();
        debug.append("=== DEBUG JOIN ROOM ===\n");
        debug.append("User ID: ").append(userId).append("\n");
        debug.append("Room ID: ").append(roomId).append("\n");
        
        boolean userInRoom = isUserInAnyRoom(userId);
        debug.append("User already in room: ").append(userInRoom).append("\n");
        
        boolean roomActive = isRoomActive(roomId);
        debug.append("Room is active: ").append(roomActive).append("\n");
        
        if (!userInRoom && roomActive) {
            debug.append("All checks passed - should be able to join\n");
        } else {
            debug.append("Join failed due to checks\n");
        }
        
        debug.append("========================");
        return debug.toString();
    }
    
    public void cleanupInactiveParticipants() {
        System.out.println("Cleaning up inactive participants...");
        String sql = "UPDATE roomparticipant SET is_active = false WHERE is_active = true AND joined_at < DATE_SUB(NOW(), INTERVAL 1 HOUR)";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Cleaned up " + rowsAffected + " inactive participants");
            
        } catch (SQLException e) {
            System.err.println("Cleanup error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public List<Item> getItemQueue(Long roomId) {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE room_id = ? ORDER BY created_at ASC";
        
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
                }
            }
        } catch (SQLException e) {
            System.err.println("Get item queue error: " + e.getMessage());
            e.printStackTrace();
        }
        
        return items;
    }
    
    public Item getCurrentAuctionItem(Long roomId) {
        String sql = "SELECT * FROM items WHERE room_id = ? AND status = 'AUCTIONING' ORDER BY created_at ASC LIMIT 1";
        
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
        String sql = "SELECT * FROM items WHERE room_id = ? AND status = 'PENDING' ORDER BY created_at ASC LIMIT 1";
        
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
    
    public boolean startNextItemAuction(Long roomId) {
        Item currentItem = getCurrentAuctionItem(roomId);
        if (currentItem != null) {
            endItemAuction(currentItem.getId(), "ENDED");
        }
        
        Item nextItem = getNextPendingItem(roomId);
        if (nextItem != null) {
            return startItemAuction(nextItem.getId());
        }
        
        return false;
    }
    
    private boolean startItemAuction(Long itemId) {
        String sql = "UPDATE items SET status = 'AUCTIONING' WHERE id = ? AND status = 'PENDING'";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, itemId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("Start item auction error: " + e.getMessage());
        return false;
        }
    }
    
    private boolean endItemAuction(Long itemId, String status) {
        String sql = "UPDATE items SET status = ?, solt_at = ? WHERE id = ?";
        
        try (Connection conn = JDBCConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(LocalDateTime.now()));
            stmt.setLong(3, itemId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            System.err.println("End item auction error: " + e.getMessage());
            return false;
        }
    }
}
