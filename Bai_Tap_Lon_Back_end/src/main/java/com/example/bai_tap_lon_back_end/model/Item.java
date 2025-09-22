package com.example.bai_tap_lon_back_end.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Double startPrice;
    @NotNull
    private Double buyNowPrice;
    @NotNull
    private Double currentPrice;
    @NotNull
    private String status;
    @NotNull
    private LocalDateTime created_at;
    @NotNull
    private String solt_at;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private AuctionRoom room;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;
}
