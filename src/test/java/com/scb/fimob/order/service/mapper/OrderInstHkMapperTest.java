package com.scb.fimob.order.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderInstHkMapperTest {

    private OrderInstHkMapper orderInstHkMapper;

    @BeforeEach
    public void setUp() {
        orderInstHkMapper = new OrderInstHkMapperImpl();
    }
}
