package com.atease.booking.service.mapper;

import com.atease.booking.domain.BahRate;
import com.atease.booking.service.dto.BahRateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BahRate} and its DTO {@link BahRateDTO}.
 */
@Mapper(componentModel = "spring")
public interface BahRateMapper extends EntityMapper<BahRateDTO, BahRate> {}
