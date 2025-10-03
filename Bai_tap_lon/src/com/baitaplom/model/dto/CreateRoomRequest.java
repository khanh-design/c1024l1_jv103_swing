/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baitaplom.model.dto;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class CreateRoomRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String roomName;
    private Long ownerId;
    
    public CreateRoomRequest() {}
    
    public CreateRoomRequest(String roomName, Long ownerId) {
        this.roomName = roomName;
        this.ownerId = ownerId;
    }
    
    public String getRoomName() {
        return roomName;
    }
    
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
    
    public Long getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
    
    @Override
    public String toString() {
        return "CreateRoomRequest{" + "roomName=" + roomName + ", ownerId=" + ownerId + '}';
    }
}
