package com.atease.booking.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InstallationMapperTest {

    private InstallationMapper installationMapper;

    @BeforeEach
    public void setUp() {
        installationMapper = new InstallationMapperImpl();
    }
}
