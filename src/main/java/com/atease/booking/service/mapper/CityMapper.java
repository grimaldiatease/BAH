package com.atease.booking.service.mapper;

import com.atease.booking.domain.City;
import com.atease.booking.service.dto.CityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {}
