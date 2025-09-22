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

public class Transaction {
    private Long id;
    private Double final_price;
    private LocalDateTime created_at;
    private String transaction_type;
    private String status;
    private Long item_id;
    private Long buyer_id;

    public Transaction() {

    }

    public Transaction(Long id, Double final_price, LocalDateTime created_at, String transaction_type, String status, Long item_id, Long buyer_id) {
        this.id = id;
        this.final_price = final_price;
        this.created_at = created_at;
        this.transaction_type = transaction_type;
        this.status = status;
        this.item_id = item_id;
        this.buyer_id = buyer_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFinal_price() {
        return final_price;
    }

    public void setFinal_price(Double final_price) {
        this.final_price = final_price;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }

    public Long getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(Long buyer_id) {
        this.buyer_id = buyer_id;
    }
}


