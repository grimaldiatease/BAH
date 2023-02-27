package com.atease.booking.service;

import com.atease.booking.domain.FamilyMember;
import com.atease.booking.repository.FamilyMemberRepository;
import com.atease.booking.service.dto.FamilyMemberDTO;
import com.atease.booking.service.mapper.FamilyMemberMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FamilyMember}.
 */
@Service
@Transactional
public class FamilyMemberService {

    private final Logger log = LoggerFactory.getLogger(FamilyMemberService.class);

    private final FamilyMemberRepository familyMemberRepository;

    private final FamilyMemberMapper familyMemberMapper;

    public FamilyMemberService(FamilyMemberRepository familyMemberRepository, FamilyMemberMapper familyMemberMapper) {
        this.familyMemberRepository = familyMemberRepository;
        this.familyMemberMapper = familyMemberMapper;
    }

    /**
     * Save a familyMember.
     *
     * @param familyMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public FamilyMemberDTO save(FamilyMemberDTO familyMemberDTO) {
        log.debug("Request to save FamilyMember : {}", familyMemberDTO);
        FamilyMember familyMember = familyMemberMapper.toEntity(familyMemberDTO);
        familyMember = familyMemberRepository.save(familyMember);
        return familyMemberMapper.toDto(familyMember);
    }

    /**
     * Update a familyMember.
     *
     * @param familyMemberDTO the entity to save.
     * @return the persisted entity.
     */
    public FamilyMemberDTO update(FamilyMemberDTO familyMemberDTO) {
        log.debug("Request to update FamilyMember : {}", familyMemberDTO);
        FamilyMember familyMember = familyMemberMapper.toEntity(familyMemberDTO);
        familyMember = familyMemberRepository.save(familyMember);
        return familyMemberMapper.toDto(familyMember);
    }

    /**
     * Partially update a familyMember.
     *
     * @param familyMemberDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FamilyMemberDTO> partialUpdate(FamilyMemberDTO familyMemberDTO) {
        log.debug("Request to partially update FamilyMember : {}", familyMemberDTO);

        return familyMemberRepository
            .findById(familyMemberDTO.getId())
            .map(existingFamilyMember -> {
                familyMemberMapper.partialUpdate(existingFamilyMember, familyMemberDTO);

                return existingFamilyMember;
            })
            .map(familyMemberRepository::save)
            .map(familyMemberMapper::toDto);
    }

    /**
     * Get all the familyMembers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FamilyMemberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FamilyMembers");
        return familyMemberRepository.findAll(pageable).map(familyMemberMapper::toDto);
    }

    /**
     * Get one familyMember by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FamilyMemberDTO> findOne(Long id) {
        log.debug("Request to get FamilyMember : {}", id);
        return familyMemberRepository.findById(id).map(familyMemberMapper::toDto);
    }

    /**
     * Delete the familyMember by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FamilyMember : {}", id);
        familyMemberRepository.deleteById(id);
    }
}
