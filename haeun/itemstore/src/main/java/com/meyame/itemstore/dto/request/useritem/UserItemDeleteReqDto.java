package com.meyame.itemstore.dto.request.useritem;

public record UserItemDeleteReqDto (
        Long itemId,
        int deleteQuantity
){
}
