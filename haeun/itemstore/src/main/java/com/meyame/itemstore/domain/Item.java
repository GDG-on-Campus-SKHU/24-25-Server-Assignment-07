package com.meyame.itemstore.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 물품명

    private int price; // 가격

    @JsonIgnore
    @OneToMany(mappedBy = "item",fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserItem> userItemList = new ArrayList<>(); // item 이 담긴 UserItem 목록

    @Builder
    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void update(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
