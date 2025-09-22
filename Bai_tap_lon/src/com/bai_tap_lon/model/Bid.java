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

public class Bid {
    private Long id;
    private Double price;
    private LocalDateTime created_at;
    private Long item_id;
    private Long user_id;

    public Bid() {

    }

    public Bid(Long id, Double price, LocalDateTime created_at, Long item_id, Long user_id) {
        this.id = id;
        this.price = price;
        this.created_at = created_at;
        this.item_id = item_id;
        this.user_id = user_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
