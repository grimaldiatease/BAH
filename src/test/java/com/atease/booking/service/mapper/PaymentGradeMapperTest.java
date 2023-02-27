package com.atease.booking.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentGradeMapperTest {

    private PaymentGradeMapper paymentGradeMapper;

    @BeforeEach
    public void setUp() {
        paymentGradeMapper = new PaymentGradeMapperImpl();
    }
}
