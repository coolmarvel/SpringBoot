package com.example.shoppingmall.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CartOrderDto {

    private Long cartItemId;
    private List<CartOrderDto> cartOrderDtoList;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public List<CartOrderDto> getCartOrderDtoList() {
        return cartOrderDtoList;
    }

    public void setCartOrderDtoList(List<CartOrderDto> cartOrderDtoList) {
        this.cartOrderDtoList = cartOrderDtoList;
    }

}
