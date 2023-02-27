package com.atease.booking.repository;

import com.atease.booking.domain.PaymentGrade;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentGrade entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentGradeRepository extends JpaRepository<PaymentGrade, Long> {}
