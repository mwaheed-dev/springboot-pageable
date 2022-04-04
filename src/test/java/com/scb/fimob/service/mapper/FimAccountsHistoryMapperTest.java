package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FimAccountsHistoryMapperTest {

    private FimAccountsHistoryMapper fimAccountsHistoryMapper;

    @BeforeEach
    public void setUp() {
        fimAccountsHistoryMapper = new FimAccountsHistoryMapperImpl();
    }
}
