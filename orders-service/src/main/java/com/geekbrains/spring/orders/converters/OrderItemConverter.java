package com.geekbrains.spring.orders.converters;

import com.geekbrains.spring.orders.entities.OrderItem;
import com.geekbrains.spring.web.api.dto.OrderItemDto;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {

    public OrderItemDto entityToDto(OrderItem orderItem){
        return new OrderItemDto(orderItem.getProductId(), orderItem.getProductTitle(),
                orderItem.getQuantity(), orderItem.getPricePerProduct(), orderItem.getPrice());
    }
}
