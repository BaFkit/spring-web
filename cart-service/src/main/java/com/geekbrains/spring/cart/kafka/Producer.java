package com.geekbrains.spring.cart.kafka;

import com.geekbrains.spring.cart.converters.ConverterCartDto;
import com.geekbrains.spring.cart.dto.Cart;
import com.geekbrains.spring.cart.services.CartService;
import com.geekbrains.spring.web.api.dto.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
@RequiredArgsConstructor
public class Producer {

    private final CartService cartService;
    private final KafkaTemplate<Long, CartDto> kafkaTemplate;
    private final ConverterCartDto converterCartDto;

    public void sendCurrenCart(String cartName) {
        System.out.println("Producer in cartService: " + cartName);
        Cart cart = cartService.getCurrentCart(cartName);
        ListenableFuture<SendResult<Long, CartDto>> future = kafkaTemplate.send("currentCart", converterCartDto.entityToDto(cart));
        future.addCallback(System.out::println, System.err::println);
        kafkaTemplate.flush();
    }
}
