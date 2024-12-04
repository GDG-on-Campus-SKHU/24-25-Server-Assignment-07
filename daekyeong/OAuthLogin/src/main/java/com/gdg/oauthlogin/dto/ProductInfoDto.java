package com.gdg.oauthlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ProductInfoDto {
    private Long id;
    private String category;
    private String name;
    private String description;
    private Long price;
    private String publisherName;
}
