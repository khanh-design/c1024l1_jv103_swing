package com.example.bai_tap_lon_back_end.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AuctionRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String status;
    @NotNull
    private LocalDateTime created_at;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;
}
