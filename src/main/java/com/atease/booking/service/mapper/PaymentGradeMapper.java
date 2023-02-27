package com.atease.booking.service.mapper;

import com.atease.booking.domain.BahRate;
import com.atease.booking.domain.Guest;
import com.atease.booking.domain.PaymentGrade;
import com.atease.booking.service.dto.BahRateDTO;
import com.atease.booking.service.dto.GuestDTO;
import com.atease.booking.service.dto.PaymentGradeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentGrade} and its DTO {@link PaymentGradeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentGradeMapper extends EntityMapper<PaymentGradeDTO, PaymentGrade> {
    @Mapping(target = "guest", source = "guest", qualifiedByName = "guestId")
    @Mapping(target = "bahRate", source = "bahRate", qualifiedByName = "bahRateId")
    PaymentGradeDTO toDto(PaymentGrade s);

    @Named("guestId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GuestDTO toDtoGuestId(Guest guest);

    @Named("bahRateId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BahRateDTO toDtoBahRateId(BahRate bahRate);
}
