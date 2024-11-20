package com.meyame.itemstore.repository;

import com.meyame.itemstore.domain.Item;
import com.meyame.itemstore.domain.UserItem;
import com.meyame.itemstore.domain.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    Optional<UserItem> findByUserAndItem(User user, Item item);
    List<UserItem> findByUser(User user);
    Optional<UserItem> findByItem_IdAndUser(Long aLong, User user);
}
