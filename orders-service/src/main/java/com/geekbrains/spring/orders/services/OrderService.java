package com.geekbrains.spring.orders.services;

import com.geekbrains.spring.orders.entities.Order;
import com.geekbrains.spring.orders.entities.OrderItem;
import com.geekbrains.spring.orders.repositories.OrderRepository;
import com.geekbrains.spring.web.api.dto.OrderDto;
import com.geekbrains.spring.web.api.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    @Value("${spring.kafka.topic}")
    private String topic;

    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic}")
    public void saveOrder(OrderDto orderDto){
        Order order = new Order();
        order.setAddress(orderDto.getAddress());
        order.setPhone(orderDto.getPhone());
        order.setUsername(orderDto.getUsername());
        order.setTotalPrice(orderDto.getTotalPrice());
        List<OrderItem> items = orderDto.getItemDtoList().stream()
                .map(o -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setQuantity(o.getQuantity());
                    orderItem.setPricePerProduct(o.getPricePerProduct());
                    orderItem.setPrice(o.getPrice());
                    orderItem.setProductId(o.getProductId());
                    orderItem.setProductTitle(o.getTitle());
                    return orderItem;
                }).collect(Collectors.toList());
        order.setItems(items);
        orderRepository.save(order);
    }

    public List<Order> findOrdersByUsername(String username) {
        try {
            return orderRepository.findByUsername(username);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public Order findById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }
}
