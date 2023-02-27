package com.atease.booking.service.mapper;

import com.atease.booking.domain.PaymentGrade;
import com.atease.booking.service.dto.PaymentGradeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentGrade} and its DTO {@link PaymentGradeDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentGradeMapper extends EntityMapper<PaymentGradeDTO, PaymentGrade> {}
