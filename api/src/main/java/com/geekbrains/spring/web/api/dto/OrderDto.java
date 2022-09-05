package com.geekbrains.spring.web.api.dto;

import java.util.List;

public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItemDto> itemDtoList;
    private Integer totalPrice;
    private String address;
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<OrderItemDto> getItemDtoList() {
        return itemDtoList;
    }

    public void setItemDtoList(List<OrderItemDto> itemDtoList) {
        this.itemDtoList = itemDtoList;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
