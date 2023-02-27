package com.atease.booking.service.mapper;

import com.atease.booking.domain.FamilyMember;
import com.atease.booking.domain.Guest;
import com.atease.booking.service.dto.FamilyMemberDTO;
import com.atease.booking.service.dto.GuestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FamilyMember} and its DTO {@link FamilyMemberDTO}.
 */
@Mapper(componentModel = "spring")
public interface FamilyMemberMapper extends EntityMapper<FamilyMemberDTO, FamilyMember> {
    @Mapping(target = "guest", source = "guest", qualifiedByName = "guestId")
    FamilyMemberDTO toDto(FamilyMember s);

    @Named("guestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GuestDTO toDtoGuestId(Guest guest);
}
