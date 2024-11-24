package com.meyame.itemstore.dto.request.item;

import com.meyame.itemstore.domain.Item;

public record ItemRegisterReqDto (
        String name,
        int price
){
    public Item toEntity() {
        return Item.builder()
                .name(name)
                .price(price)
                .build();
    }
}
