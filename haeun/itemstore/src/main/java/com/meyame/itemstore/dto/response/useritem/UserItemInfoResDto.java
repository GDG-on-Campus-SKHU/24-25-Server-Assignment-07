package com.meyame.itemstore.dto.response.useritem;

import com.meyame.itemstore.domain.UserItem;
import lombok.Builder;

@Builder
public record UserItemInfoResDto (
        Long id,
        String userName, // 유저명
        Long itemId,
        String itemName, // 아이템명
        int quantity
){
    public static UserItemInfoResDto from (UserItem userItem) {
        return UserItemInfoResDto.builder()
                .id(userItem.getId())
                .userName(userItem.getUser().getName())
                .itemId(userItem.getItem().getId())
                .itemName(userItem.getItem().getName())
                .quantity(userItem.getQuantity())
                .build();
    }
}
