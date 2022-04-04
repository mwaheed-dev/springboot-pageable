package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FimCustHistoryMapperTest {

    private FimCustHistoryMapper fimCustHistoryMapper;

    @BeforeEach
    public void setUp() {
        fimCustHistoryMapper = new FimCustHistoryMapperImpl();
    }
}
