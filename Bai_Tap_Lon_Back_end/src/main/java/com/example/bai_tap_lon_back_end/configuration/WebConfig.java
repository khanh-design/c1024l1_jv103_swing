package com.example.bai_tap_lon_back_end.configuration;

import com.example.bai_tap_lon_back_end.formatter.AuctionRoomFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuctionRoomFormatter auctionRoomFormatter;

    public WebConfig(AuctionRoomFormatter auctionRoomFormatter) {
        this.auctionRoomFormatter = auctionRoomFormatter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(auctionRoomFormatter);
    }
}

