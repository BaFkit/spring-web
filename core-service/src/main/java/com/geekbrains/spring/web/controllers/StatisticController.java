package com.geekbrains.spring.web.controllers;

import com.geekbrains.spring.web.dto.StatisticDto;
import com.geekbrains.spring.web.services.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService statisticService;

    @PostMapping
    public StatisticDto getStatistic() {
        return statisticService.getStatistic();
    }
}
