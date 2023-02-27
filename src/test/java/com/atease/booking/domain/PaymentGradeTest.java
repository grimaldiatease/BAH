package com.atease.booking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentGradeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentGrade.class);
        PaymentGrade paymentGrade1 = new PaymentGrade();
        paymentGrade1.setId(1L);
        PaymentGrade paymentGrade2 = new PaymentGrade();
        paymentGrade2.setId(paymentGrade1.getId());
        assertThat(paymentGrade1).isEqualTo(paymentGrade2);
        paymentGrade2.setId(2L);
        assertThat(paymentGrade1).isNotEqualTo(paymentGrade2);
        paymentGrade1.setId(null);
        assertThat(paymentGrade1).isNotEqualTo(paymentGrade2);
    }
}
