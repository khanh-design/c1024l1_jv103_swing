package com.example.bai_tap_lon_back_end.service.impl;

import com.example.bai_tap_lon_back_end.model.Bid;
import com.example.bai_tap_lon_back_end.repository.IBidRepository;
import com.example.bai_tap_lon_back_end.service.IBidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BidService implements IBidService {
    @Autowired
    private IBidRepository iBidRepository;

    @Override
    public Iterable<Bid> findAll() {
        return iBidRepository.findAll();
    }

    @Override
    public Optional<Bid> findById(Long id) {
        return iBidRepository.findById(id);
    }

    @Override
    public Bid save(Bid bid) {
        return iBidRepository.save(bid);
    }

    @Override
    public void delete(Long id) {
        iBidRepository.deleteById(id);
    }
}
