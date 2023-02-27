package com.atease.booking.repository;

import com.atease.booking.domain.FamilyMember;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FamilyMember entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, Long> {}
