package com.meyame.itemstore.dto.response.item;

import com.meyame.itemstore.domain.Item;
import lombok.Builder;

@Builder
public record ItemInfoResDto(
        Long id,
        String name,
        int price
){
    public static ItemInfoResDto from(Item item) {
        return ItemInfoResDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .build();
    }
}
