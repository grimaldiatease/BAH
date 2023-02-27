package com.atease.booking.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BahRateMapperTest {

    private BahRateMapper bahRateMapper;

    @BeforeEach
    public void setUp() {
        bahRateMapper = new BahRateMapperImpl();
    }
}
