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
public class AuctionRoom implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String name;
    private String status;
    private LocalDateTime created_at;
    private Long owner_id;

    public AuctionRoom() {}
    
    public AuctionRoom(Long id, String name, String status, LocalDateTime created_at, Long owner_id) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.created_at = created_at;
        this.owner_id = owner_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Long getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(Long owner_id) {
        this.owner_id = owner_id;
    }
    
    @Override
    public String toString() {
        return "AuctionRoom{" + "id=" + id + ", name=" + name + ", status=" + status + ", created_at=" + created_at + ", owner_id=" + owner_id + '}';
    }
}
