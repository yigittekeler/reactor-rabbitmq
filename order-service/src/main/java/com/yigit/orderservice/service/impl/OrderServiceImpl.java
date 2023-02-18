package com.yigit.orderservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Connection;
import com.yigit.orderservice.dto.CreateOrderRequestDto;
import com.yigit.orderservice.entity.Order;
import com.yigit.orderservice.repository.OrderRepository;
import com.yigit.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.Receiver;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final Receiver receiver;
    private static final String QUEUE = "order-queue";
    private final OrderRepository orderRepository;

    @Autowired
    private Mono<Connection> connectionMono;

    @PostConstruct
    private void init()  {
        createOrder();
    }

    @PreDestroy
    public void close() throws Exception {
        connectionMono.block().close();
    }


    @Override
    public void createOrder() {
        receiver.consumeAutoAck(QUEUE)
                .doOnError(e -> log.error("error",e))
                .flatMap(m -> {
                    Order order = null;
                    try {
                        order = transformData(m.getBody());

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return Flux.just(order);
                } )
                .subscribe(m -> log.info("consume success"));
    }

    private Order transformData(byte[] m) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = SerializationUtils.deserialize(m);
        CreateOrderRequestDto createOrderRequestDto = null;
        createOrderRequestDto = mapper.readValue(json, CreateOrderRequestDto.class);
        Order order = new Order();
        order.setProductId(createOrderRequestDto.getProductId());
        Mono<Order> savedOrder = orderRepository.save(order);
        return savedOrder.block();
    }


    @Override
    public Mono<Order> createOrderManual(CreateOrderRequestDto dto) {
        Order order = new Order();
        order.setProductId(dto.getProductId());
        return orderRepository.save(order);
    }
}
