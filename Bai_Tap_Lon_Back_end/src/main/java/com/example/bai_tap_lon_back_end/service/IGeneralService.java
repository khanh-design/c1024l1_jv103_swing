package com.example.bai_tap_lon_back_end.service;

import com.example.bai_tap_lon_back_end.model.AuctionRoom;

import java.util.Optional;

public interface IGeneralService<T> {
    Iterable<T> findAll();
    Optional<T> findById(Long id);
    T save(T t);
    void delete(Long id);
}
