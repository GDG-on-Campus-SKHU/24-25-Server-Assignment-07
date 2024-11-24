package com.meyame.itemstore.service;

import com.meyame.itemstore.domain.Item;
import com.meyame.itemstore.domain.UserItem;
import com.meyame.itemstore.domain.auth.User;
import com.meyame.itemstore.dto.request.useritem.UserItemDeleteReqDto;
import com.meyame.itemstore.dto.request.useritem.UserItemStoreReqDto;
import com.meyame.itemstore.dto.response.useritem.UserItemInfoResDto;
import com.meyame.itemstore.exception.custom.CustomException;
import com.meyame.itemstore.repository.ItemRepository;
import com.meyame.itemstore.repository.UserItemRepository;
import com.meyame.itemstore.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.meyame.itemstore.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserItemService {

    private final UserItemRepository userItemRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public UserItemInfoResDto storeItem(UserItemStoreReqDto userItemStoreReqDto, Long userId) {
        // 각각의 ID를 통해 User 와 Item 의 정보를 받아온다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Item item = itemRepository.findById(userItemStoreReqDto.itemId())
                .orElseThrow(() -> new CustomException(ITEM_NOT_FOUND));

        // UserItem 을 조회하여
        // 1) 기존 UserItem 이 존재하면 -> 수량 업데이트
        // 2) 기존 UserItem 이 없으면 -> 새로 생성
        UserItem userItem = userItemRepository.findByUserAndItem(user, item)
                .orElseGet(() -> UserItem.createUserItem(user,item));

        // 수량 업데이트
        userItem.addQuantity(userItemStoreReqDto.quantity());
        userItemRepository.save(userItem);

        return UserItemInfoResDto.from(userItem);
    }

    @Transactional(readOnly = true)
    public List<UserItemInfoResDto> findAllStoredItem(Long userId) {
        // 사용자 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        // 아이템 조회
        List<UserItem> userItems = userItemRepository.findByUser(user);

        return userItems.stream()
                .map(UserItemInfoResDto::from)
                .toList();
    }

    @Transactional
    public void deleteUserItem(UserItemDeleteReqDto userItemDeleteReqDto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        // itemId 와 User를 통해 UserItem을 찾는다.
        UserItem userItem = userItemRepository.findByItem_IdAndUser(userItemDeleteReqDto.itemId(),user)
                        .orElseThrow(() -> new CustomException(USER_ITEM_NOT_FOUND));
        // 현재 남아있는 수량에서 삭제하고자 하는 수량만큼 빼준다.
        // 만약 수량이 0이라면, 완전히 제거한다.
        userItem.minusQuantity(userItemDeleteReqDto.deleteQuantity());
        if(userItem.getQuantity() == 0){
            userItemRepository.delete(userItem);
        }
    }
}
