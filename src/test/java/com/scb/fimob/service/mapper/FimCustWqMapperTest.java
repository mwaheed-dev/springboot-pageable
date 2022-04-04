package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FimCustWqMapperTest {

    private FimCustWqMapper fimCustWqMapper;

    @BeforeEach
    public void setUp() {
        fimCustWqMapper = new FimCustWqMapperImpl();
    }
}
