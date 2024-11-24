package com.meyame.itemstore.dto.request.useritem;

import com.meyame.itemstore.domain.Item;
import com.meyame.itemstore.domain.UserItem;
import com.meyame.itemstore.domain.auth.User;
import lombok.Builder;

@Builder
public record UserItemStoreReqDto (
        int quantity,
        Long userId,
        Long itemId
){
    public UserItem toEntity(User user, Item item) {
        return UserItem.builder()
                .quantity(quantity)
                .user(user)
                .item(item)
                .build();
    }
}
