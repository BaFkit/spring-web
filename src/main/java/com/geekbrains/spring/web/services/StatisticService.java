package com.geekbrains.spring.web.services;

import com.geekbrains.spring.web.dto.StatisticDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class StatisticService {

    private long durationProductsService;
    private long durationUserService;
    private long durationOrderService;


    public StatisticDto getStatistic() {
        StatisticDto statisticDto = new StatisticDto();
        statisticDto.setDurationProductsService(this.durationProductsService);
        statisticDto.setDurationOrderService(this.durationOrderService);
        statisticDto.setDurationUserService(this.durationUserService);
        return statisticDto;
    }
}
