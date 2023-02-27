package com.atease.booking.service.mapper;

import com.atease.booking.domain.Guest;
import com.atease.booking.domain.Installation;
import com.atease.booking.service.dto.GuestDTO;
import com.atease.booking.service.dto.InstallationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Installation} and its DTO {@link InstallationDTO}.
 */
@Mapper(componentModel = "spring")
public interface InstallationMapper extends EntityMapper<InstallationDTO, Installation> {
    @Mapping(target = "guest", source = "guest", qualifiedByName = "guestId")
    InstallationDTO toDto(Installation s);

    @Named("guestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GuestDTO toDtoGuestId(Guest guest);
}
