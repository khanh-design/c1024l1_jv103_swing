package com.baitaplom.model.dto;

public class CreateItemRequest {
    private String name;
    private String description;
    private Double startPrice;
    private Double buyNowPrice;
    private Long sellerId;
    private Long roomId;
    private String images;
    
    public CreateItemRequest() {
        
    }
    
    public CreateItemRequest(String name, String description, Double startPrice, Double buyNowPrice, Long sellerId, Long roomId, String images) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.buyNowPrice = buyNowPrice;
        this.sellerId = sellerId;
        this.roomId = roomId;
        this.images = images;
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
        return "CreateItemRequest{" + "name=" + name + ", description=" + description + ", startPrice=" + startPrice + ", buyNowPrice=" + buyNowPrice + ", sellerId=" + sellerId + ", roomId=" + roomId + ", images=" + images + '}';
    }
}
