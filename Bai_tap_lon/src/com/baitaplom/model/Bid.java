/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baitaplom.model;

import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class Bid {
    private Long id;
    private Double amount;
    private LocalDateTime created_at;
    private Long userId;
    private Long itemId;

    public Bid() {}

    public Bid(Long id, Double amount, LocalDateTime created_at) {
        this.id = id;
        this.amount = amount;
        this.created_at = created_at;
    }

    public Bid(Long id, Double amount, LocalDateTime created_at, Long userId, Long itemId) {
        this.id = id;
        this.amount = amount;
        this.created_at = created_at;
        this.userId = userId;
        this.itemId = itemId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    
    @Override
    public String toString() {
        return "Bid{" + "id=" + id + ", amount=" + amount + ", created_at=" + created_at + ", userId=" + userId + ", itemId=" + itemId + '}';
    }
}
