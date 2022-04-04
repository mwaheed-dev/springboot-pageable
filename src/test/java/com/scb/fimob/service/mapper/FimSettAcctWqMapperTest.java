package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FimSettAcctWqMapperTest {

    private FimSettAcctWqMapper fimSettAcctWqMapper;

    @BeforeEach
    public void setUp() {
        fimSettAcctWqMapper = new FimSettAcctWqMapperImpl();
    }
}
