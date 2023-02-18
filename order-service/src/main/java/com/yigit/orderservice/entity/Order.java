package com.yigit.orderservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Document("order")
public class Order implements Serializable {

    @Id
    private String id;
    private Long productId;
}
