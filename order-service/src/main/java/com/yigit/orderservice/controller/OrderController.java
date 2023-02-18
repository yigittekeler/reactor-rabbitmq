package com.yigit.orderservice.controller;

import com.yigit.orderservice.dto.CreateOrderRequestDto;
import com.yigit.orderservice.entity.Order;
import com.yigit.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/create/manual")
    Mono<Order> createOrderManual(@RequestBody CreateOrderRequestDto dto) {
        return orderService.createOrderManual(dto);
    }
}
