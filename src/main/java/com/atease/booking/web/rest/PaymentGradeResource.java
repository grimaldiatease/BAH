package com.atease.booking.web.rest;

import com.atease.booking.repository.PaymentGradeRepository;
import com.atease.booking.service.PaymentGradeService;
import com.atease.booking.service.dto.PaymentGradeDTO;
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
 * REST controller for managing {@link com.atease.booking.domain.PaymentGrade}.
 */
@RestController
@RequestMapping("/api")
public class PaymentGradeResource {

    private final Logger log = LoggerFactory.getLogger(PaymentGradeResource.class);

    private static final String ENTITY_NAME = "bookingservicePaymentGrade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentGradeService paymentGradeService;

    private final PaymentGradeRepository paymentGradeRepository;

    public PaymentGradeResource(PaymentGradeService paymentGradeService, PaymentGradeRepository paymentGradeRepository) {
        this.paymentGradeService = paymentGradeService;
        this.paymentGradeRepository = paymentGradeRepository;
    }

    /**
     * {@code POST  /payment-grades} : Create a new paymentGrade.
     *
     * @param paymentGradeDTO the paymentGradeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentGradeDTO, or with status {@code 400 (Bad Request)} if the paymentGrade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-grades")
    public ResponseEntity<PaymentGradeDTO> createPaymentGrade(@RequestBody PaymentGradeDTO paymentGradeDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentGrade : {}", paymentGradeDTO);
        if (paymentGradeDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentGrade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentGradeDTO result = paymentGradeService.save(paymentGradeDTO);
        return ResponseEntity
            .created(new URI("/api/payment-grades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-grades/:id} : Updates an existing paymentGrade.
     *
     * @param id the id of the paymentGradeDTO to save.
     * @param paymentGradeDTO the paymentGradeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentGradeDTO,
     * or with status {@code 400 (Bad Request)} if the paymentGradeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentGradeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-grades/{id}")
    public ResponseEntity<PaymentGradeDTO> updatePaymentGrade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentGradeDTO paymentGradeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentGrade : {}, {}", id, paymentGradeDTO);
        if (paymentGradeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentGradeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentGradeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentGradeDTO result = paymentGradeService.update(paymentGradeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentGradeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-grades/:id} : Partial updates given fields of an existing paymentGrade, field will ignore if it is null
     *
     * @param id the id of the paymentGradeDTO to save.
     * @param paymentGradeDTO the paymentGradeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentGradeDTO,
     * or with status {@code 400 (Bad Request)} if the paymentGradeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentGradeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentGradeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-grades/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentGradeDTO> partialUpdatePaymentGrade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PaymentGradeDTO paymentGradeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentGrade partially : {}, {}", id, paymentGradeDTO);
        if (paymentGradeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentGradeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentGradeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentGradeDTO> result = paymentGradeService.partialUpdate(paymentGradeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentGradeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-grades} : get all the paymentGrades.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentGrades in body.
     */
    @GetMapping("/payment-grades")
    public ResponseEntity<List<PaymentGradeDTO>> getAllPaymentGrades(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of PaymentGrades");
        Page<PaymentGradeDTO> page = paymentGradeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payment-grades/:id} : get the "id" paymentGrade.
     *
     * @param id the id of the paymentGradeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentGradeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-grades/{id}")
    public ResponseEntity<PaymentGradeDTO> getPaymentGrade(@PathVariable Long id) {
        log.debug("REST request to get PaymentGrade : {}", id);
        Optional<PaymentGradeDTO> paymentGradeDTO = paymentGradeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentGradeDTO);
    }

    /**
     * {@code DELETE  /payment-grades/:id} : delete the "id" paymentGrade.
     *
     * @param id the id of the paymentGradeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-grades/{id}")
    public ResponseEntity<Void> deletePaymentGrade(@PathVariable Long id) {
        log.debug("REST request to delete PaymentGrade : {}", id);
        paymentGradeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
