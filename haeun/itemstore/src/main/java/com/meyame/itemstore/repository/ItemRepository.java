package com.meyame.itemstore.repository;

import com.meyame.itemstore.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> getItemById(Long itemId);
}
