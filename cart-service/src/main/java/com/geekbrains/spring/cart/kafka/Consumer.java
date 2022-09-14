package com.geekbrains.spring.cart.kafka;


import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Consumer {

    private final Producer producer;

    @KafkaListener(topics = "cartName")
    public void cartNameListener(String cartName) {
        producer.sendCurrenCart(cartName);
    }
}
