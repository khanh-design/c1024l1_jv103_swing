package com.example.bai_tap_lon_back_end.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Roomparticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private LocalDateTime joined_at;
    @NotNull
    private boolean is_active;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private AuctionRoom room;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
