package com.example.bai_tap_lon_back_end.service.impl;

import com.example.bai_tap_lon_back_end.model.AuctionRoom;
import com.example.bai_tap_lon_back_end.repository.IAuctionRoomRepository;
import com.example.bai_tap_lon_back_end.service.IAuctionRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuctionRoomSerivce implements IAuctionRoomService {
    @Autowired
    private IAuctionRoomRepository iAuctionRoomRepository;

    @Override
    public Iterable<AuctionRoom> findAll() {
        return iAuctionRoomRepository.findAll();
    }

    @Override
    public Optional<AuctionRoom> findById(Long id) {
        return iAuctionRoomRepository.findById(id);
    }

    @Override
    public AuctionRoom save(AuctionRoom auctionRoom) {
        iAuctionRoomRepository.save(auctionRoom);
        return auctionRoom;
    }


    @Override
    public void delete(Long id) {
        iAuctionRoomRepository.deleteById(id);
    }
}
