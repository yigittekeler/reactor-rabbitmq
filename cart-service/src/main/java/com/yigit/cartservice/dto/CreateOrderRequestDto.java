package com.yigit.cartservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CreateOrderRequestDto implements Serializable {


    private Long productId;
}
