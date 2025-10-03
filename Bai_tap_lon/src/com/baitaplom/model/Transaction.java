/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baitaplom.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Admin
 */
public class Transaction {
        private Long id;
    private Double finalPrice;
    private LocalDateTime created_at;
    private String description_type;
    private String status;

    public Transaction(Long id, Double finalPrice, LocalDateTime created_at, String description_type, String status) {
        this.id = id;
        this.finalPrice = finalPrice;
        this.created_at = created_at;
        this.description_type = description_type;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getDescription_type() {
        return description_type;
    }

    public void setDescription_type(String description_type) {
        this.description_type = description_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
