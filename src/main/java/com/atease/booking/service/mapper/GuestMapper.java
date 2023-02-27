package com.atease.booking.service.mapper;

import com.atease.booking.domain.Department;
import com.atease.booking.domain.Guest;
import com.atease.booking.domain.Installation;
import com.atease.booking.domain.PaymentGrade;
import com.atease.booking.service.dto.DepartmentDTO;
import com.atease.booking.service.dto.GuestDTO;
import com.atease.booking.service.dto.InstallationDTO;
import com.atease.booking.service.dto.PaymentGradeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Guest} and its DTO {@link GuestDTO}.
 */
@Mapper(componentModel = "spring")
public interface GuestMapper extends EntityMapper<GuestDTO, Guest> {
    @Mapping(target = "paymentGrade", source = "paymentGrade", qualifiedByName = "paymentGradeId")
    @Mapping(target = "installation", source = "installation", qualifiedByName = "installationId")
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentId")
    GuestDTO toDto(Guest s);

    @Named("paymentGradeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentGradeDTO toDtoPaymentGradeId(PaymentGrade paymentGrade);

    @Named("installationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    InstallationDTO toDtoInstallationId(Installation installation);

    @Named("departmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DepartmentDTO toDtoDepartmentId(Department department);
}
