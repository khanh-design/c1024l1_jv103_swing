/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baitaplom.server;

import com.baitaplom.configuration.JDBCConfig;
import com.baitaplom.model.AuctionRoom;
import com.baitaplom.model.User;
import com.baitaplom.model.dto.CreateRoomRequest;
import com.baitaplom.model.dto.GetRoomsRequest;
import com.baitaplom.model.dto.JoinRoomRequest;
import com.baitaplom.model.dto.LoginRequest;
import com.baitaplom.model.dto.Message;
import com.baitaplom.model.dto.RegisterRequest;
import com.baitaplom.service.RoomService;
import com.baitaplom.service.UserService;
import java.util.List;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class AuctionServer {
    
    private static final int PORT = 12345;
    private ServerSocket serverSocket;
    private UserService userService;
    private RoomService roomService;
    
    public AuctionServer() {
        this.userService = new UserService();
        this.roomService = new RoomService();
    }
    
    public void start() {
        try {
            
            serverSocket = new ServerSocket(PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                
                // Xử lý client trong thread riêng
                new Thread(() -> handleClient(clientSocket)).start();
            }
            
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
    
    private void handleClient(Socket clientSocket) {
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {
            
            while (true) {
                Message request = (Message) ois.readObject();
                Message response = processRequest(request);
                oos.writeObject(response);
                oos.flush();
            }
            
        } catch (IOException | ClassNotFoundException e) {
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
            }
        }
    }
    
    private Message processRequest(Message request) {
        switch (request.getType()) {
            case LOGIN_REQUEST:
                return handleLogin((LoginRequest) request.getData());
            case REGISTER_REQUEST:
                return handleRegister((RegisterRequest) request.getData());
            case CREATE_ROOM_REQUEST:
                return handleCreateRoom((CreateRoomRequest) request.getData());
            case JOIN_ROOM_REQUEST:
                return handleJoinRoom((JoinRoomRequest) request.getData());
            case GET_ROOMS_REQUEST:
                return handleGetRooms((GetRoomsRequest) request.getData());
            case LEAVE_ROOM_REQUEST:
                return handleLeaveRoom((Long) request.getData());
            default:
                return new Message(Message.MessageType.ERROR, null, "Unknown request type", false);
        }
    }
    
    private Message handleLogin(LoginRequest loginRequest) {
        try {
            User user = userService.login(loginRequest);
            if (user != null) {
                return new Message(Message.MessageType.LOGIN_RESPONSE, user, "Login successful", true);
            } else {
                return new Message(Message.MessageType.LOGIN_RESPONSE, null, "Invalid username or password", false);
            }
        } catch (Exception e) {
            return new Message(Message.MessageType.ERROR, null, "Login error: " + e.getMessage(), false);
        }
    }
    
    private Message handleRegister(RegisterRequest registerRequest) {
        try {
            boolean success = userService.register(registerRequest);
            if (success) {
                return new Message(Message.MessageType.REGISTER_RESPONSE, null, "Registration successful", true);
            } else {
                return new Message(Message.MessageType.REGISTER_RESPONSE, null, "Username or email already exists", false);
            }
        } catch (Exception e) {
            return new Message(Message.MessageType.ERROR, null, "Registration error: " + e.getMessage(), false);
        }
    }
    
    private Message handleCreateRoom(CreateRoomRequest createRoomRequest) {
        try {
            AuctionRoom room = roomService.createRoom(createRoomRequest);
            if (room != null) {
                return new Message(Message.MessageType.CREATE_ROOM_RESPONSE, room, "Room created successfully", true);
            } else {
                return new Message(Message.MessageType.CREATE_ROOM_RESPONSE, null, "Failed to create room", false);
            }
        } catch (Exception e) {
            return new Message(Message.MessageType.ERROR, null, "Create room error: " + e.getMessage(), false);
        }
    }
    
    private Message handleJoinRoom(JoinRoomRequest joinRoomRequest) {
        try {
            boolean success = roomService.joinRoom(joinRoomRequest);
            if (success) {
                return new Message(Message.MessageType.JOIN_ROOM_RESPONSE, null, "Joined room successfully", true);
            } else {
                return new Message(Message.MessageType.JOIN_ROOM_RESPONSE, null, "Failed to join room. You may already be in a room or room is not available", false);
            }
        } catch (Exception e) {
            return new Message(Message.MessageType.ERROR, null, "Join room error: " + e.getMessage(), false);
        }
    }
    
    private Message handleGetRooms(GetRoomsRequest getRoomsRequest) {
        try {
            List<AuctionRoom> rooms = roomService.getAllActiveRooms();
            return new Message(Message.MessageType.GET_ROOMS_RESPONSE, rooms, "Rooms retrieved successfully", true);
        } catch (Exception e) {
            return new Message(Message.MessageType.ERROR, null, "Get rooms error: " + e.getMessage(), false);
        }
    }
    
    private Message handleLeaveRoom(Long userId) {
        try {
            boolean success = roomService.leaveRoom(userId);
            if (success) {
                return new Message(Message.MessageType.LEAVE_ROOM_RESPONSE, null, "Left room successfully", true);
            } else {
                return new Message(Message.MessageType.LEAVE_ROOM_RESPONSE, null, "Failed to leave room", false);
            }
        } catch (Exception e) {
            return new Message(Message.MessageType.ERROR, null, "Leave room error: " + e.getMessage(), false);
        }
    }
    
    public void stop() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                System.out.println("Server stopped");
            }
        } catch (IOException e) {
            System.err.println("Error stopping server: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        AuctionServer server = new AuctionServer();
        server.start();
    }
}
