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
public class JoinRoomRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long roomId;
    private Long userId;
    
    public JoinRoomRequest() {}
    
    public JoinRoomRequest(Long roomId, Long userId) {
        this.roomId = roomId;
        this.userId = userId;
    }
    
    public Long getRoomId() {
        return roomId;
    }
    
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    @Override
    public String toString() {
        return "JoinRoomRequest{" + "roomId=" + roomId + ", userId=" + userId + '}';
    }
}
