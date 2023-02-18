package com.yigit.orderservice.service;

import com.yigit.orderservice.dto.CreateOrderRequestDto;
import com.yigit.orderservice.entity.Order;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<Order> createOrderManual(CreateOrderRequestDto dto);

    void createOrder();
}
