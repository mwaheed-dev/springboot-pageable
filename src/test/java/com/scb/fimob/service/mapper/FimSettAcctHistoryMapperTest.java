package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FimSettAcctHistoryMapperTest {

    private FimSettAcctHistoryMapper fimSettAcctHistoryMapper;

    @BeforeEach
    public void setUp() {
        fimSettAcctHistoryMapper = new FimSettAcctHistoryMapperImpl();
    }
}
