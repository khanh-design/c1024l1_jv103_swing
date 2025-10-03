package com.baitaplom.model;

import java.time.LocalDateTime;

public class Item {
    private Long id;
    private String name;
    private String description;
    private Double startPrice;
    private Double buyNowPrice;
    private Double currentPrice;
    private String status;
    private LocalDateTime created_at;
    private String solt_at;
    private Long sellerId;
    private Long roomId;
    private String images;
    
    public Item() {
        
    }
    
    public Item(long aLong) {
        
    }
    
    public Item(Long id, String name, String description, Double startPrice, Double buyNowPrice, Double currentPrice, String status, LocalDateTime created_at, String solt_at) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.buyNowPrice = buyNowPrice;
        this.currentPrice = currentPrice;
        this.status = status;
        this.created_at = created_at;
        this.solt_at = solt_at;
    }
    
    public Item(Long id, String name, String description, Double startPrice, Double buyNowPrice, Double currentPrice, String status, LocalDateTime created_at, String solt_at, Long sellerId, Long roomId, String images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.buyNowPrice = buyNowPrice;
        this.currentPrice = currentPrice;
        this.status = status;
        this.created_at = created_at;
        this.solt_at = solt_at;
        this.sellerId = sellerId;
        this.roomId = roomId;
        this.images = images;
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

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getBuyNowPrice() {
        return buyNowPrice;
    }

    public void setBuyNowPrice(Double buyNowPrice) {
        this.buyNowPrice = buyNowPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
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

    public String getSolt_at() {
        return solt_at;
    }

    public void setSolt_at(String solt_at) {
        this.solt_at = solt_at;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
    
    @Override
    public String toString() {
        return name + " - $" + (currentPrice != null ? currentPrice : startPrice);
    }
}
