package com.example.assignment07.dto.BookDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookRequestDto {
    private String title;
    private String author;
    private double price;
    private int stock;
}
