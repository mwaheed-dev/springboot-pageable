package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FimAccountsWqMapperTest {

    private FimAccountsWqMapper fimAccountsWqMapper;

    @BeforeEach
    public void setUp() {
        fimAccountsWqMapper = new FimAccountsWqMapperImpl();
    }
}
