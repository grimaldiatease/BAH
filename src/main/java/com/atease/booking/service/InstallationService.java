package com.atease.booking.service;

import com.atease.booking.domain.Installation;
import com.atease.booking.repository.InstallationRepository;
import com.atease.booking.service.dto.InstallationDTO;
import com.atease.booking.service.mapper.InstallationMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Installation}.
 */
@Service
@Transactional
public class InstallationService {

    private final Logger log = LoggerFactory.getLogger(InstallationService.class);

    private final InstallationRepository installationRepository;

    private final InstallationMapper installationMapper;

    public InstallationService(InstallationRepository installationRepository, InstallationMapper installationMapper) {
        this.installationRepository = installationRepository;
        this.installationMapper = installationMapper;
    }

    /**
     * Save a installation.
     *
     * @param installationDTO the entity to save.
     * @return the persisted entity.
     */
    public InstallationDTO save(InstallationDTO installationDTO) {
        log.debug("Request to save Installation : {}", installationDTO);
        Installation installation = installationMapper.toEntity(installationDTO);
        installation = installationRepository.save(installation);
        return installationMapper.toDto(installation);
    }

    /**
     * Update a installation.
     *
     * @param installationDTO the entity to save.
     * @return the persisted entity.
     */
    public InstallationDTO update(InstallationDTO installationDTO) {
        log.debug("Request to update Installation : {}", installationDTO);
        Installation installation = installationMapper.toEntity(installationDTO);
        installation = installationRepository.save(installation);
        return installationMapper.toDto(installation);
    }

    /**
     * Partially update a installation.
     *
     * @param installationDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<InstallationDTO> partialUpdate(InstallationDTO installationDTO) {
        log.debug("Request to partially update Installation : {}", installationDTO);

        return installationRepository
            .findById(installationDTO.getId())
            .map(existingInstallation -> {
                installationMapper.partialUpdate(existingInstallation, installationDTO);

                return existingInstallation;
            })
            .map(installationRepository::save)
            .map(installationMapper::toDto);
    }

    /**
     * Get all the installations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InstallationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Installations");
        return installationRepository.findAll(pageable).map(installationMapper::toDto);
    }

    /**
     * Get one installation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InstallationDTO> findOne(Long id) {
        log.debug("Request to get Installation : {}", id);
        return installationRepository.findById(id).map(installationMapper::toDto);
    }

    /**
     * Delete the installation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Installation : {}", id);
        installationRepository.deleteById(id);
    }
}
