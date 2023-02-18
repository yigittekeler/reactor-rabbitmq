package com.yigit.cartservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yigit.cartservice.dto.CreateOrderRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService{

    private final Sender sender;
    private static final String QUEUE = "order-queue";


    @Override
    public Mono<Boolean> createOrder(CreateOrderRequestDto dto) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dto);
        byte[] orderByteArray = SerializationUtils.serialize(json);
        Flux<OutboundMessage> outboundFlux = Flux.just( new OutboundMessage(
                "",
                QUEUE, orderByteArray));

        sender.declareQueue(QueueSpecification.queue(QUEUE))
                .thenMany(sender.sendWithPublishConfirms(outboundFlux))
                .doOnError(e -> log.error("send failed with: ", e))
                .subscribeOn(Schedulers.immediate())
                .subscribe(r -> {
                    if (r.isAck()) {
                        log.info("Message sent successfully");
                    }
                });

        return Mono.just(Boolean.TRUE);

    }
}
