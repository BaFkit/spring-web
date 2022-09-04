package com.geekbrains.spring.web.dto;

import lombok.Data;

@Data
public class StatisticDto {

    private long durationProductsService;
    private long durationUserService;
    private long durationOrderService;

}
