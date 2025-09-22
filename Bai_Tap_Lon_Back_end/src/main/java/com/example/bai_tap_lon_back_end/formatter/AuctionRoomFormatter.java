package com.example.bai_tap_lon_back_end.formatter;

import com.example.bai_tap_lon_back_end.model.AuctionRoom;
import com.example.bai_tap_lon_back_end.service.IAuctionRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;
import java.util.Optional;

@Component
public class AuctionRoomFormatter implements Formatter<AuctionRoom> {
    @Autowired
    private IAuctionRoomService iAuctionRoomService;

    @Override
    public AuctionRoom parse(String text, Locale locale) throws ParseException {
        Optional<AuctionRoom> auctionRoom = iAuctionRoomService.findById(Long.parseLong(text));
        return auctionRoom.get();
    }

    @Override
    public String print(AuctionRoom object, Locale locale) {
        return "[" + object.getId() + ", " + object.getName() + "]";
    }
}
