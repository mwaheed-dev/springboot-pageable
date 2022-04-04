package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FimAccountsMapperTest {

    private FimAccountsMapper fimAccountsMapper;

    @BeforeEach
    public void setUp() {
        fimAccountsMapper = new FimAccountsMapperImpl();
    }
}
