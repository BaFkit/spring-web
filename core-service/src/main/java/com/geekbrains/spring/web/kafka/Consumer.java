package com.geekbrains.spring.web.kafka;

import com.geekbrains.spring.web.api.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Consumer {

    private CartDto cartDto;

    public CartDto getCart() {
        return cartDto;
    }

    @KafkaListener(topics = "currentCart")
    public void getCurrentCart(CartDto cartDto) {
        this.cartDto = cartDto;
    }

}
