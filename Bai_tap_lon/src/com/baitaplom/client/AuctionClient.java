/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baitaplom.client;

import com.baitaplom.model.AuctionRoom;
import com.baitaplom.model.User;
import com.baitaplom.model.dto.CreateRoomRequest;
import com.baitaplom.model.dto.GetRoomsRequest;
import com.baitaplom.model.dto.JoinRoomRequest;
import com.baitaplom.model.dto.LoginRequest;
import com.baitaplom.model.dto.Message;
import com.baitaplom.model.dto.RegisterRequest;
import java.util.List;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Admin
 */
public class AuctionClient {
    
    private static final String SERVER_HOST = "26.82.69.187";
    private static final int SERVER_PORT = 12345;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public boolean connect() {
        try {
            socket = new Socket(SERVER_HOST, SERVER_PORT);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");
            return true;
        } catch (IOException e) {
            System.err.println("Connection failed: " + e.getMessage());
            return false;
        }
    }
    
    public User login(String username, String password) {
        try {
            LoginRequest loginRequest = new LoginRequest(username, password);
            Message request = new Message(Message.MessageType.LOGIN_REQUEST, loginRequest, null, false);
            
            oos.writeObject(request);
            oos.flush();
            
            Message response = (Message) ois.readObject();
            
            if (response.isSuccess()) {
                return (User) response.getData();
            } else {
                System.err.println("Login failed: " + response.getMessage());
                return null;
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Login error: " + e.getMessage());
            return null;
        }
    }
    
    public boolean register(String username, String fullname, String email, String phone, String password) {
        try {
            RegisterRequest registerRequest = new RegisterRequest(username, fullname, email, phone, password);
            Message request = new Message(Message.MessageType.REGISTER_REQUEST, registerRequest, null, false);
            
            oos.writeObject(request);
            oos.flush();
            
            Message response = (Message) ois.readObject();
            
            if (response.isSuccess()) {
                System.out.println("Registration successful: " + response.getMessage());
                return true;
            } else {
                System.err.println("Registration failed: " + response.getMessage());
                return false;
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Registration error: " + e.getMessage());
            return false;
        }
    }
    
    public void disconnect() {
        try {
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            if (socket != null) socket.close();
            System.out.println("Disconnected from server");
        } catch (IOException e) {
            System.err.println("Error disconnecting: " + e.getMessage());
        }
    }
    
    public AuctionRoom createRoom(String roomName, Long ownerId) {
        try {
            CreateRoomRequest createRoomRequest = new CreateRoomRequest(roomName, ownerId);
            Message request = new Message(Message.MessageType.CREATE_ROOM_REQUEST, createRoomRequest, null, false);
            
            oos.writeObject(request);
            oos.flush();
            
            Message response = (Message) ois.readObject();
            
            if (response.isSuccess()) {
                return (AuctionRoom) response.getData();
            } else {
                System.err.println("Create room failed: " + response.getMessage());
                return null;
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Create room error: " + e.getMessage());
            return null;
        }
    }
    
    public boolean joinRoom(Long roomId, Long userId) {
        try {
            JoinRoomRequest joinRoomRequest = new JoinRoomRequest(roomId, userId);
            Message request = new Message(Message.MessageType.JOIN_ROOM_REQUEST, joinRoomRequest, null, false);
            
            oos.writeObject(request);
            oos.flush();
            
            Message response = (Message) ois.readObject();
            
            if (response.isSuccess()) {
                System.out.println("Join room successful: " + response.getMessage());
                return true;
            } else {
                System.err.println("Join room failed: " + response.getMessage());
                return false;
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Join room error: " + e.getMessage());
            return false;
        }
    }
    
    public List<AuctionRoom> getRooms() {
        try {
            GetRoomsRequest getRoomsRequest = new GetRoomsRequest();
            Message request = new Message(Message.MessageType.GET_ROOMS_REQUEST, getRoomsRequest, null, false);
            
            oos.writeObject(request);
            oos.flush();
            
            Message response = (Message) ois.readObject();
            
            if (response.isSuccess()) {
                @SuppressWarnings("unchecked")
                List<AuctionRoom> rooms = (List<AuctionRoom>) response.getData();
                return rooms;
            } else {
                System.err.println("Get rooms failed: " + response.getMessage());
                return null;
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Get rooms error: " + e.getMessage());
            return null;
        }
    }
    
    public boolean leaveRoom(Long userId) {
        try {
            Message request = new Message(Message.MessageType.LEAVE_ROOM_REQUEST, userId, null, false);
            
            oos.writeObject(request);
            oos.flush();
            
            Message response = (Message) ois.readObject();
            
            if (response.isSuccess()) {
                System.out.println("Leave room successful: " + response.getMessage());
                return true;
            } else {
                System.err.println("Leave room failed: " + response.getMessage());
                return false;
            }
            
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Leave room error: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isConnected() {
        return socket != null && !socket.isClosed();
    }
}
