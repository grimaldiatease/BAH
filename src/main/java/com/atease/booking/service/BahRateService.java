package com.atease.booking.service;

import com.atease.booking.domain.BahRate;
import com.atease.booking.repository.BahRateRepository;
import com.atease.booking.service.dto.BahRateDTO;
import com.atease.booking.service.mapper.BahRateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BahRate}.
 */
@Service
@Transactional
public class BahRateService {

    private final Logger log = LoggerFactory.getLogger(BahRateService.class);

    private final BahRateRepository bahRateRepository;

    private final BahRateMapper bahRateMapper;

    public BahRateService(BahRateRepository bahRateRepository, BahRateMapper bahRateMapper) {
        this.bahRateRepository = bahRateRepository;
        this.bahRateMapper = bahRateMapper;
    }

    /**
     * Save a bahRate.
     *
     * @param bahRateDTO the entity to save.
     * @return the persisted entity.
     */
    public BahRateDTO save(BahRateDTO bahRateDTO) {
        log.debug("Request to save BahRate : {}", bahRateDTO);
        BahRate bahRate = bahRateMapper.toEntity(bahRateDTO);
        bahRate = bahRateRepository.save(bahRate);
        return bahRateMapper.toDto(bahRate);
    }

    /**
     * Update a bahRate.
     *
     * @param bahRateDTO the entity to save.
     * @return the persisted entity.
     */
    public BahRateDTO update(BahRateDTO bahRateDTO) {
        log.debug("Request to update BahRate : {}", bahRateDTO);
        BahRate bahRate = bahRateMapper.toEntity(bahRateDTO);
        bahRate = bahRateRepository.save(bahRate);
        return bahRateMapper.toDto(bahRate);
    }

    /**
     * Partially update a bahRate.
     *
     * @param bahRateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BahRateDTO> partialUpdate(BahRateDTO bahRateDTO) {
        log.debug("Request to partially update BahRate : {}", bahRateDTO);

        return bahRateRepository
            .findById(bahRateDTO.getId())
            .map(existingBahRate -> {
                bahRateMapper.partialUpdate(existingBahRate, bahRateDTO);

                return existingBahRate;
            })
            .map(bahRateRepository::save)
            .map(bahRateMapper::toDto);
    }

    /**
     * Get all the bahRates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BahRateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BahRates");
        return bahRateRepository.findAll(pageable).map(bahRateMapper::toDto);
    }

    /**
     * Get one bahRate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BahRateDTO> findOne(Long id) {
        log.debug("Request to get BahRate : {}", id);
        return bahRateRepository.findById(id).map(bahRateMapper::toDto);
    }

    /**
     * Delete the bahRate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BahRate : {}", id);
        bahRateRepository.deleteById(id);
    }
}
