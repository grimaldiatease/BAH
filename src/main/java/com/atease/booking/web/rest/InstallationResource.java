package com.atease.booking.web.rest;

import com.atease.booking.repository.InstallationRepository;
import com.atease.booking.service.InstallationService;
import com.atease.booking.service.dto.InstallationDTO;
import com.atease.booking.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.atease.booking.domain.Installation}.
 */
@RestController
@RequestMapping("/api")
public class InstallationResource {

    private final Logger log = LoggerFactory.getLogger(InstallationResource.class);

    private static final String ENTITY_NAME = "bookingserviceInstallation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstallationService installationService;

    private final InstallationRepository installationRepository;

    public InstallationResource(InstallationService installationService, InstallationRepository installationRepository) {
        this.installationService = installationService;
        this.installationRepository = installationRepository;
    }

    /**
     * {@code POST  /installations} : Create a new installation.
     *
     * @param installationDTO the installationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new installationDTO, or with status {@code 400 (Bad Request)} if the installation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/installations")
    public ResponseEntity<InstallationDTO> createInstallation(@RequestBody InstallationDTO installationDTO) throws URISyntaxException {
        log.debug("REST request to save Installation : {}", installationDTO);
        if (installationDTO.getId() != null) {
            throw new BadRequestAlertException("A new installation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstallationDTO result = installationService.save(installationDTO);
        return ResponseEntity
            .created(new URI("/api/installations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /installations/:id} : Updates an existing installation.
     *
     * @param id the id of the installationDTO to save.
     * @param installationDTO the installationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated installationDTO,
     * or with status {@code 400 (Bad Request)} if the installationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the installationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/installations/{id}")
    public ResponseEntity<InstallationDTO> updateInstallation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InstallationDTO installationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Installation : {}, {}", id, installationDTO);
        if (installationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, installationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!installationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InstallationDTO result = installationService.update(installationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, installationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /installations/:id} : Partial updates given fields of an existing installation, field will ignore if it is null
     *
     * @param id the id of the installationDTO to save.
     * @param installationDTO the installationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated installationDTO,
     * or with status {@code 400 (Bad Request)} if the installationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the installationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the installationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/installations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InstallationDTO> partialUpdateInstallation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody InstallationDTO installationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Installation partially : {}, {}", id, installationDTO);
        if (installationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, installationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!installationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InstallationDTO> result = installationService.partialUpdate(installationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, installationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /installations} : get all the installations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of installations in body.
     */
    @GetMapping("/installations")
    public ResponseEntity<List<InstallationDTO>> getAllInstallations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Installations");
        Page<InstallationDTO> page = installationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /installations/:id} : get the "id" installation.
     *
     * @param id the id of the installationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the installationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/installations/{id}")
    public ResponseEntity<InstallationDTO> getInstallation(@PathVariable Long id) {
        log.debug("REST request to get Installation : {}", id);
        Optional<InstallationDTO> installationDTO = installationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(installationDTO);
    }

    /**
     * {@code DELETE  /installations/:id} : delete the "id" installation.
     *
     * @param id the id of the installationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/installations/{id}")
    public ResponseEntity<Void> deleteInstallation(@PathVariable Long id) {
        log.debug("REST request to delete Installation : {}", id);
        installationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
