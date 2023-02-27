package com.atease.booking.repository;

import com.atease.booking.domain.BahRate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the BahRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BahRateRepository extends JpaRepository<BahRate, Long> {}
