package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.api.dto.CartDto;
import com.geekbrains.spring.web.api.dto.OrderDetailsDto;
import com.geekbrains.spring.web.api.dto.OrderDto;
import com.geekbrains.spring.web.converters.OrderConverter;
import com.geekbrains.spring.web.kafka.Consumer;
import com.geekbrains.spring.web.kafka.Producer;
import com.geekbrains.spring.web.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;
    private final Consumer consumer;
    private final Producer producer;
    private CartDto currentCart;

    @PostMapping("/{cartName}")
    public void createOrder(@RequestHeader String username, @RequestBody OrderDetailsDto orderDetailsDto, @PathVariable String cartName){
        producer.requestCurrentCart(cartName);
        while (currentCart == null) {currentCart = consumer.getCart();
        }
        orderService.createOrder(username, orderDetailsDto, currentCart);
    }

    @GetMapping
    public List<OrderDto> getCurrentOrders(@RequestHeader String username){
        return orderService.findOrdersByUsername(username).stream()
                .map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}