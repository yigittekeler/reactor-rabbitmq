package com.yigit.cartservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yigit.cartservice.dto.CreateOrderRequestDto;
import reactor.core.publisher.Mono;

public interface CartService {

    Mono<Boolean> createOrder(CreateOrderRequestDto dto) throws JsonProcessingException;
}
