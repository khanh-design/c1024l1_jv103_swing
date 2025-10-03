/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baitaplom.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class Roomparticipant implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private Long user_id;
    private Long room_id;
    private LocalDateTime joined_at;
    private boolean is_active;

    public Roomparticipant() {}
    
    public Roomparticipant(Long id, Long user_id, Long room_id, LocalDateTime joined_at, boolean is_active) {
        this.id = id;
        this.user_id = user_id;
        this.room_id = room_id;
        this.joined_at = joined_at;
        this.is_active = is_active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getJoined_at() {
        return joined_at;
    }

    public void setJoined_at(LocalDateTime joined_at) {
        this.joined_at = joined_at;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }
    
    @Override
    public String toString() {
        return "Roomparticipant{" + "id=" + id + ", user_id=" + user_id + ", room_id=" + room_id + ", joined_at=" + joined_at + ", is_active=" + is_active + '}';
    }
}
