package com.atease.booking.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BahRateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BahRate.class);
        BahRate bahRate1 = new BahRate();
        bahRate1.setId(1L);
        BahRate bahRate2 = new BahRate();
        bahRate2.setId(bahRate1.getId());
        assertThat(bahRate1).isEqualTo(bahRate2);
        bahRate2.setId(2L);
        assertThat(bahRate1).isNotEqualTo(bahRate2);
        bahRate1.setId(null);
        assertThat(bahRate1).isNotEqualTo(bahRate2);
    }
}
