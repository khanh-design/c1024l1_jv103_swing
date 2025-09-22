/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bai_tap_lon.model;

/**
 *
 * @author Admin
 */
import java.time.LocalDateTime;

public class Room_Participant {
    private Long id;
    private LocalDateTime joined_at;
    private boolean is_active;
    private Long room_id;
    private Long user_id;

    public Room_Participant() {

    }

    public Room_Participant(Long id, LocalDateTime joined_at, boolean is_active, Long room_id, Long user_id) {
        this.id = id;
        this.joined_at = joined_at;
        this.is_active = is_active;
        this.room_id = room_id;
        this.user_id = user_id;
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

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
