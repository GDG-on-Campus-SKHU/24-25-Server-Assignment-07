package com.meyame.itemstore.service;

import com.meyame.itemstore.domain.Item;
import com.meyame.itemstore.dto.request.item.ItemUpdateReqDto;
import com.meyame.itemstore.dto.response.item.ItemInfoResDto;
import com.meyame.itemstore.dto.request.item.ItemRegisterReqDto;
import com.meyame.itemstore.exception.custom.CustomException;
import com.meyame.itemstore.repository.ItemRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.meyame.itemstore.exception.ErrorCode.ITEM_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 아이템 등록
    @Transactional
    public ItemInfoResDto registerItem(ItemRegisterReqDto itemRegisterReqDto) {
        Item item = itemRegisterReqDto.toEntity();
        itemRepository.save(item);

        return ItemInfoResDto.from(item);
    }

    // 아이템 한 개 조회
    @Transactional(readOnly = true)
    public ItemInfoResDto findItemById(Long itemId) {
        Item item = itemRepository.getItemById(itemId)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));

        return ItemInfoResDto.from(item);
    }

    // 등록된 모든 아이템 조회
    @Transactional(readOnly = true)
    public List<ItemInfoResDto> findAllItems() {
        List<Item> items = itemRepository.findAll();

        return items.stream()
                .map(ItemInfoResDto::from)
                .toList();
    }

    // 아이템 수정
    @Transactional
    public ItemInfoResDto updateItemById(Long itemId, ItemUpdateReqDto itemUpdateReqDto) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));
        item.update(itemUpdateReqDto.name(), itemUpdateReqDto.price());

        return ItemInfoResDto.from(item);
    }

    // 아이템 삭제
    @Transactional
    public void deleteItemById(Long itemId) {
        itemRepository.deleteById(itemId);
    }

}
