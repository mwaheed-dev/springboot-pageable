package com.scb.fimob.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EthnicityMapperTest {

    private EthnicityMapper ethnicityMapper;

    @BeforeEach
    public void setUp() {
        ethnicityMapper = new EthnicityMapperImpl();
    }
}
