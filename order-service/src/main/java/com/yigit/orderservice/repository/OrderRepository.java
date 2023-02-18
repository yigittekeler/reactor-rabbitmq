package com.yigit.orderservice.repository;

import com.yigit.orderservice.entity.Order;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ReactiveMongoRepository<Order, String> {


}
