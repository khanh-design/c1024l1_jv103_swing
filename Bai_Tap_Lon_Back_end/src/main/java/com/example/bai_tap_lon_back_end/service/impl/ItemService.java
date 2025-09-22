package com.example.bai_tap_lon_back_end.service.impl;

import com.example.bai_tap_lon_back_end.model.Item;
import com.example.bai_tap_lon_back_end.repository.IItemRepository;
import com.example.bai_tap_lon_back_end.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService implements IItemService {
    @Autowired
    private IItemRepository iItemRepository;

    @Override
    public Iterable<Item> findAll() {
        return iItemRepository.findAll();
    }

    @Override
    public Optional<Item> findById(Long id) {
        return iItemRepository.findById(id);
    }

    @Override
    public Item save(Item item) {
        return iItemRepository.save(item);
    }

    @Override
    public void delete(Long id) {
        iItemRepository.deleteById(id);
    }
}
