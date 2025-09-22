package com.example.bai_tap_lon_back_end.repository;

import com.example.bai_tap_lon_back_end.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemRepository extends JpaRepository<Item, Long> {
}
