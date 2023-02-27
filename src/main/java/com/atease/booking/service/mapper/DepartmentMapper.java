package com.atease.booking.service.mapper;

import com.atease.booking.domain.Department;
import com.atease.booking.domain.Guest;
import com.atease.booking.service.dto.DepartmentDTO;
import com.atease.booking.service.dto.GuestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
    @Mapping(target = "guest", source = "guest", qualifiedByName = "guestId")
    DepartmentDTO toDto(Department s);

    @Named("guestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GuestDTO toDtoGuestId(Guest guest);
}
