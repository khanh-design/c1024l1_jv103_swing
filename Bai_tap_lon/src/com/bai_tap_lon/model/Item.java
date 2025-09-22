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

public class Item {
    private Long id;
    private String name;
    private String description;
    private Double start_price;
    private Double buy_now_price;
    private Double current_price;
    private String status;
    private LocalDateTime created_at;
    private LocalDateTime solt_at;
    private Long room_id;
    private Long seller_id;

    public Item() {

    }

    public Item(Long id, String name, String description, Double start_price, Double buy_now_price, Double current_price, String status, LocalDateTime created_at, LocalDateTime solt_at, Long room_id, Long seller_id) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.start_price = start_price;
        this.buy_now_price = buy_now_price;
        this.current_price = current_price;
        this.status = status;
        this.created_at = created_at;
        this.solt_at = solt_at;
        this.room_id = room_id;
        this.seller_id = seller_id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getStart_price() {
        return start_price;
    }

    public void setStart_price(Double start_price) {
        this.start_price = start_price;
    }

    public Double getBuy_now_price() {
        return buy_now_price;
    }

    public void setBuy_now_price(Double buy_now_price) {
        this.buy_now_price = buy_now_price;
    }

    public Double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Double current_price) {
        this.current_price = current_price;
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

    public LocalDateTime getSolt_at() {
        return solt_at;
    }

    public void setSolt_at(LocalDateTime solt_at) {
        this.solt_at = solt_at;
    }

    public Long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(Long room_id) {
        this.room_id = room_id;
    }

    public Long getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(Long seller_id) {
        this.seller_id = seller_id;
    }
}
