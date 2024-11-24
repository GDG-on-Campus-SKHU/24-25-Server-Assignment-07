package com.gdg.seun.dto;

import com.gdg.seun.domain.Cake;
import lombok.Builder;
import lombok.Data;

@Data
public class CakeDto {
    private Long id;
    private String name;
    private Long price;

    @Builder
    public CakeDto(Long id, String name, Long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Cake toEntity() {
        return Cake.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();
    }

}
