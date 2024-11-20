package com.meyame.itemstore.domain;

import com.meyame.itemstore.domain.auth.User;
import com.meyame.itemstore.exception.custom.CustomException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.meyame.itemstore.exception.ErrorCode.*;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name="user_item",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","item_id"})}
)
// 인증된 사용자만 접근 가능한 '사용자가 보관하는 Item'
public class UserItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity; // 수량

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @Builder
    public UserItem(Long id, int quantity, User user, Item item) {
        this.id = id;
        this.quantity = quantity;
        this.user = user;
        this.item = item;
    }

    public static UserItem createUserItem(User user, Item item) {
        UserItem userItem = new UserItem();
        userItem.user = user;
        userItem.item = item;
        userItem.quantity = 0;
        return userItem;
    }

    public void addQuantity(int quantity) {
        if(quantity <=0 ) {
            throw new CustomException(INVALID_ITEM_QUANTITY);
        }
        this.quantity += quantity;
    }

    public void minusQuantity(int quantity) {
        if(quantity <= 0) { // 요청 수량이 유효하지 않음
            throw new CustomException(DELETE_INVALID_ITEM_QUANTITY);
        }
        if(this.quantity - quantity < 0) { // 결과 수량이 음수
            throw new CustomException(DELETE_INVALID_LESS_QUANTITY);
        }
        this.quantity -= quantity;
    }
}
