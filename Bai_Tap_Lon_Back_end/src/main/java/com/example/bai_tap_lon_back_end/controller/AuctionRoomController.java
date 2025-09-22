package com.example.bai_tap_lon_back_end.controller;

import com.example.bai_tap_lon_back_end.model.AuctionRoom;
import com.example.bai_tap_lon_back_end.model.User;
import com.example.bai_tap_lon_back_end.service.IAuctionRoomService;
import com.example.bai_tap_lon_back_end.service.IUserService;
import org.hibernate.Internal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auction-rooms")
public class AuctionRoomController {
    @Autowired
    private IAuctionRoomService iAuctionRoomService;

    @Autowired
    private IUserService iUserService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<AuctionRoom>> findAllAuctionRoom() {
        List<AuctionRoom> auctionRoomList = (List<AuctionRoom>) iAuctionRoomService.findAll();
        if (auctionRoomList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(auctionRoomList, HttpStatus.OK);
    }

    @GetMapping("/findbyAuctionRoom/{id}")
    public ResponseEntity<AuctionRoom> findByAuctionRoomId(@PathVariable Long id) {
        Optional<AuctionRoom> auctionRoom = iAuctionRoomService.findById(id);
        return auctionRoom.map(room -> new ResponseEntity<>(room, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("")
    public ResponseEntity<AuctionRoom> createAuctionRoom(@RequestBody AuctionRoom auctionRoom) {
        AuctionRoom saved = iAuctionRoomService.save(auctionRoom);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/updateAuctionRoom/{id}")
    public ResponseEntity<AuctionRoom> updateAuctionRoom(
            @PathVariable Long id,
            @RequestBody AuctionRoom auctionRoomRequest) {

        Optional<AuctionRoom> optionalAuctionRoom = iAuctionRoomService.findById(id);
        if (!optionalAuctionRoom.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        AuctionRoom auctionRoom = optionalAuctionRoom.get();
        auctionRoom.setName(auctionRoomRequest.getName());
        auctionRoom.setStatus(auctionRoomRequest.getStatus());

        if (auctionRoomRequest.getOwner() != null && auctionRoomRequest.getOwner().getId() != null) {
            User user = iUserService.findById(auctionRoomRequest.getOwner().getId())
                    .orElseThrow(() -> new RuntimeException("Owner not found"));
            auctionRoom.setOwner(user);
        }

        AuctionRoom updated = iAuctionRoomService.save(auctionRoom);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAuctionRoom/{id}")
    public ResponseEntity<AuctionRoom> deleteAuctionRoom(@PathVariable Long id) {
        Optional<AuctionRoom> auctionRoom = iAuctionRoomService.findById(id);
        if (!auctionRoom.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        iAuctionRoomService.delete(id);
        return new ResponseEntity<>(auctionRoom.get(), HttpStatus.OK);
    }

}