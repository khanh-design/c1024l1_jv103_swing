/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baitaplom.model.dto;

import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class InfoObjectRequest {
    private String name;
    private LocalDateTime created_at;
    private String description;
    private Double current_price;
    private String start_price;
    private String solt_at;
    private String status;
    private String images;
    
    public InfoObjectRequest() {
        
    }
    
    public InfoObjectRequest(String name, LocalDateTime created_at, String description, Double current_price, String start_price, String solt_at, String status, String images) {
        this.name = name;
        this.created_at = created_at;
        this.description = description;
        this.current_price = current_price;
        this.start_price = start_price;
        this.solt_at = solt_at;
        this.status = status;
        this.images = images;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(Double current_price) {
        this.current_price = current_price;
    }

    public String getStart_price() {
        return start_price;
    }

    public void setStart_price(String start_price) {
        this.start_price = start_price;
    }

    public String getSolt_at() {
        return solt_at;
    }

    public void setSolt_at(String solt_at) {
        this.solt_at = solt_at;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "InfoObjectRequest{" + "name=" + name + ", created_at=" + created_at + ", description=" + description + ", current_price=" + current_price + ", start_price=" + start_price + ", solt_at=" + solt_at + ", status=" + status + ", images=" + images + '}';
    }
}
