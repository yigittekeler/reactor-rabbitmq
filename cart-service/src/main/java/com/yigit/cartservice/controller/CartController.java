package com.yigit.cartservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yigit.cartservice.dto.CreateOrderRequestDto;
import com.yigit.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/orderCreate")
    public Mono<Boolean> createOrder(@RequestBody CreateOrderRequestDto dto) throws JsonProcessingException {
        return cartService.createOrder(dto);
    }
}
