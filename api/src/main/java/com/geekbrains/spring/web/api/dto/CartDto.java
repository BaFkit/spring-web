package com.geekbrains.spring.web.api.dto;

import java.util.List;

public class CartDto {
    private List<OrderItemDto> items;
    private  int totalPrice;

    public CartDto() {
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
