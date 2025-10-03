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
public class Message implements Serializable {
    
    public enum MessageType {
        LOGIN_REQUEST,
        LOGIN_RESPONSE,
        REGISTER_REQUEST,
        REGISTER_RESPONSE,
        CREATE_ROOM_REQUEST,
        CREATE_ROOM_RESPONSE,
        JOIN_ROOM_REQUEST,
        JOIN_ROOM_RESPONSE,
        GET_ROOMS_REQUEST,
        GET_ROOMS_RESPONSE,
        LEAVE_ROOM_REQUEST,
        LEAVE_ROOM_RESPONSE,
        ERROR
    }
    
    private MessageType type;
    private Object data;
    private String message;
    private boolean success;
    
    public Message() {}
    
    public Message(MessageType type, Object data, String message, boolean success) {
        this.type = type;
        this.data = data;
        this.message = message;
        this.success = success;
    }
    
    public MessageType getType() {
        return type;
    }
    
    public void setType(MessageType type) {
        this.type = type;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    @Override
    public String toString() {
        return "Message{" + "type=" + type + ", data=" + data + ", message=" + message + ", success=" + success + '}';
    }
}