package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FimCustMapperTest {

    private FimCustMapper fimCustMapper;

    @BeforeEach
    public void setUp() {
        fimCustMapper = new FimCustMapperImpl();
    }
}
