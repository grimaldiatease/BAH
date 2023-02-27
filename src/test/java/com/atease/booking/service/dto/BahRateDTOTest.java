package com.atease.booking.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BahRateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BahRateDTO.class);
        BahRateDTO bahRateDTO1 = new BahRateDTO();
        bahRateDTO1.setId(1L);
        BahRateDTO bahRateDTO2 = new BahRateDTO();
        assertThat(bahRateDTO1).isNotEqualTo(bahRateDTO2);
        bahRateDTO2.setId(bahRateDTO1.getId());
        assertThat(bahRateDTO1).isEqualTo(bahRateDTO2);
        bahRateDTO2.setId(2L);
        assertThat(bahRateDTO1).isNotEqualTo(bahRateDTO2);
        bahRateDTO1.setId(null);
        assertThat(bahRateDTO1).isNotEqualTo(bahRateDTO2);
    }
}
