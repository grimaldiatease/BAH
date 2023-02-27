package com.atease.booking.service.mapper;

import com.atease.booking.domain.City;
import com.atease.booking.domain.State;
import com.atease.booking.service.dto.CityDTO;
import com.atease.booking.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(target = "state", source = "state", qualifiedByName = "stateId")
    CityDTO toDto(City s);

    @Named("stateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    StateDTO toDtoStateId(State state);
}
