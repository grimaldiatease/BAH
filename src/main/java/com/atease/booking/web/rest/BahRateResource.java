package com.atease.booking.web.rest;

import com.atease.booking.repository.BahRateRepository;
import com.atease.booking.service.BahRateService;
import com.atease.booking.service.dto.BahRateDTO;
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
 * REST controller for managing {@link com.atease.booking.domain.BahRate}.
 */
@RestController
@RequestMapping("/api")
public class BahRateResource {

    private final Logger log = LoggerFactory.getLogger(BahRateResource.class);

    private static final String ENTITY_NAME = "bookingserviceBahRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BahRateService bahRateService;

    private final BahRateRepository bahRateRepository;

    public BahRateResource(BahRateService bahRateService, BahRateRepository bahRateRepository) {
        this.bahRateService = bahRateService;
        this.bahRateRepository = bahRateRepository;
    }

    /**
     * {@code POST  /bah-rates} : Create a new bahRate.
     *
     * @param bahRateDTO the bahRateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bahRateDTO, or with status {@code 400 (Bad Request)} if the bahRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bah-rates")
    public ResponseEntity<BahRateDTO> createBahRate(@RequestBody BahRateDTO bahRateDTO) throws URISyntaxException {
        log.debug("REST request to save BahRate : {}", bahRateDTO);
        if (bahRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new bahRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BahRateDTO result = bahRateService.save(bahRateDTO);
        return ResponseEntity
            .created(new URI("/api/bah-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bah-rates/:id} : Updates an existing bahRate.
     *
     * @param id the id of the bahRateDTO to save.
     * @param bahRateDTO the bahRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bahRateDTO,
     * or with status {@code 400 (Bad Request)} if the bahRateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bahRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bah-rates/{id}")
    public ResponseEntity<BahRateDTO> updateBahRate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BahRateDTO bahRateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update BahRate : {}, {}", id, bahRateDTO);
        if (bahRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bahRateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bahRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BahRateDTO result = bahRateService.update(bahRateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bahRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bah-rates/:id} : Partial updates given fields of an existing bahRate, field will ignore if it is null
     *
     * @param id the id of the bahRateDTO to save.
     * @param bahRateDTO the bahRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bahRateDTO,
     * or with status {@code 400 (Bad Request)} if the bahRateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bahRateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bahRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bah-rates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BahRateDTO> partialUpdateBahRate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody BahRateDTO bahRateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update BahRate partially : {}, {}", id, bahRateDTO);
        if (bahRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bahRateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bahRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BahRateDTO> result = bahRateService.partialUpdate(bahRateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bahRateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /bah-rates} : get all the bahRates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bahRates in body.
     */
    @GetMapping("/bah-rates")
    public ResponseEntity<List<BahRateDTO>> getAllBahRates(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of BahRates");
        Page<BahRateDTO> page = bahRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /bah-rates/:id} : get the "id" bahRate.
     *
     * @param id the id of the bahRateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bahRateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bah-rates/{id}")
    public ResponseEntity<BahRateDTO> getBahRate(@PathVariable Long id) {
        log.debug("REST request to get BahRate : {}", id);
        Optional<BahRateDTO> bahRateDTO = bahRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bahRateDTO);
    }

    /**
     * {@code DELETE  /bah-rates/:id} : delete the "id" bahRate.
     *
     * @param id the id of the bahRateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bah-rates/{id}")
    public ResponseEntity<Void> deleteBahRate(@PathVariable Long id) {
        log.debug("REST request to delete BahRate : {}", id);
        bahRateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
