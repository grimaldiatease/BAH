package com.atease.booking.service.mapper;

import com.atease.booking.domain.City;
import com.atease.booking.domain.State;
import com.atease.booking.service.dto.CityDTO;
import com.atease.booking.service.dto.StateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link State} and its DTO {@link StateDTO}.
 */
@Mapper(componentModel = "spring")
public interface StateMapper extends EntityMapper<StateDTO, State> {
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    StateDTO toDto(State s);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
