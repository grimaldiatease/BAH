package com.atease.booking.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.atease.booking.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InstallationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstallationDTO.class);
        InstallationDTO installationDTO1 = new InstallationDTO();
        installationDTO1.setId(1L);
        InstallationDTO installationDTO2 = new InstallationDTO();
        assertThat(installationDTO1).isNotEqualTo(installationDTO2);
        installationDTO2.setId(installationDTO1.getId());
        assertThat(installationDTO1).isEqualTo(installationDTO2);
        installationDTO2.setId(2L);
        assertThat(installationDTO1).isNotEqualTo(installationDTO2);
        installationDTO1.setId(null);
        assertThat(installationDTO1).isNotEqualTo(installationDTO2);
    }
}
