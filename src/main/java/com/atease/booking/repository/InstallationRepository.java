package com.atease.booking.repository;

import com.atease.booking.domain.Installation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Installation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {}
