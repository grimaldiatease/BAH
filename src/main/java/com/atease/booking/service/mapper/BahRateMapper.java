package com.atease.booking.service.mapper;

import com.atease.booking.domain.BahRate;
import com.atease.booking.domain.City;
import com.atease.booking.domain.PaymentGrade;
import com.atease.booking.service.dto.BahRateDTO;
import com.atease.booking.service.dto.CityDTO;
import com.atease.booking.service.dto.PaymentGradeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BahRate} and its DTO {@link BahRateDTO}.
 */
@Mapper(componentModel = "spring")
public interface BahRateMapper extends EntityMapper<BahRateDTO, BahRate> {
    @Mapping(target = "paymentGrade", source = "paymentGrade", qualifiedByName = "paymentGradeId")
    @Mapping(target = "city", source = "city", qualifiedByName = "cityId")
    BahRateDTO toDto(BahRate s);

    @Named("paymentGradeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentGradeDTO toDtoPaymentGradeId(PaymentGrade paymentGrade);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
