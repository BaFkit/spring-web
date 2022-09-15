package com.geekbrains.spring.cart.converters;

import com.geekbrains.spring.cart.dto.Cart;
import com.geekbrains.spring.web.api.dto.CartDto;
import org.springframework.stereotype.Component;

@Component
public class ConverterCartDto {

    public CartDto entityToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setItems(cart.getItems());
        return cartDto;
    }
}
