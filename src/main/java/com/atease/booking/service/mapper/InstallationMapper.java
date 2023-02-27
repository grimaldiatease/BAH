package com.atease.booking.service.mapper;

import com.atease.booking.domain.Installation;
import com.atease.booking.service.dto.InstallationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Installation} and its DTO {@link InstallationDTO}.
 */
@Mapper(componentModel = "spring")
public interface InstallationMapper extends EntityMapper<InstallationDTO, Installation> {}
