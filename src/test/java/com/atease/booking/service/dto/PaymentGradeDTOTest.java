package com.atease.booking.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentGradeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentGradeDTO.class);
        PaymentGradeDTO paymentGradeDTO1 = new PaymentGradeDTO();
        paymentGradeDTO1.setId(1L);
        PaymentGradeDTO paymentGradeDTO2 = new PaymentGradeDTO();
        assertThat(paymentGradeDTO1).isNotEqualTo(paymentGradeDTO2);
        paymentGradeDTO2.setId(paymentGradeDTO1.getId());
        assertThat(paymentGradeDTO1).isEqualTo(paymentGradeDTO2);
        paymentGradeDTO2.setId(2L);
        assertThat(paymentGradeDTO1).isNotEqualTo(paymentGradeDTO2);
        paymentGradeDTO1.setId(null);
        assertThat(paymentGradeDTO1).isNotEqualTo(paymentGradeDTO2);
    }
}
