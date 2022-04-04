package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FimSettAcctMapperTest {

    private FimSettAcctMapper fimSettAcctMapper;

    @BeforeEach
    public void setUp() {
        fimSettAcctMapper = new FimSettAcctMapperImpl();
    }
}
