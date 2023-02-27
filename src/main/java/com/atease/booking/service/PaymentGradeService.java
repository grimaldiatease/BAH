package com.atease.booking.service;

import com.atease.booking.domain.PaymentGrade;
import com.atease.booking.repository.PaymentGradeRepository;
import com.atease.booking.service.dto.PaymentGradeDTO;
import com.atease.booking.service.mapper.PaymentGradeMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentGrade}.
 */
@Service
@Transactional
public class PaymentGradeService {

    private final Logger log = LoggerFactory.getLogger(PaymentGradeService.class);

    private final PaymentGradeRepository paymentGradeRepository;

    private final PaymentGradeMapper paymentGradeMapper;

    public PaymentGradeService(PaymentGradeRepository paymentGradeRepository, PaymentGradeMapper paymentGradeMapper) {
        this.paymentGradeRepository = paymentGradeRepository;
        this.paymentGradeMapper = paymentGradeMapper;
    }

    /**
     * Save a paymentGrade.
     *
     * @param paymentGradeDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentGradeDTO save(PaymentGradeDTO paymentGradeDTO) {
        log.debug("Request to save PaymentGrade : {}", paymentGradeDTO);
        PaymentGrade paymentGrade = paymentGradeMapper.toEntity(paymentGradeDTO);
        paymentGrade = paymentGradeRepository.save(paymentGrade);
        return paymentGradeMapper.toDto(paymentGrade);
    }

    /**
     * Update a paymentGrade.
     *
     * @param paymentGradeDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentGradeDTO update(PaymentGradeDTO paymentGradeDTO) {
        log.debug("Request to update PaymentGrade : {}", paymentGradeDTO);
        PaymentGrade paymentGrade = paymentGradeMapper.toEntity(paymentGradeDTO);
        paymentGrade = paymentGradeRepository.save(paymentGrade);
        return paymentGradeMapper.toDto(paymentGrade);
    }

    /**
     * Partially update a paymentGrade.
     *
     * @param paymentGradeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentGradeDTO> partialUpdate(PaymentGradeDTO paymentGradeDTO) {
        log.debug("Request to partially update PaymentGrade : {}", paymentGradeDTO);

        return paymentGradeRepository
            .findById(paymentGradeDTO.getId())
            .map(existingPaymentGrade -> {
                paymentGradeMapper.partialUpdate(existingPaymentGrade, paymentGradeDTO);

                return existingPaymentGrade;
            })
            .map(paymentGradeRepository::save)
            .map(paymentGradeMapper::toDto);
    }

    /**
     * Get all the paymentGrades.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentGradeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentGrades");
        return paymentGradeRepository.findAll(pageable).map(paymentGradeMapper::toDto);
    }

    /**
     * Get one paymentGrade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentGradeDTO> findOne(Long id) {
        log.debug("Request to get PaymentGrade : {}", id);
        return paymentGradeRepository.findById(id).map(paymentGradeMapper::toDto);
    }

    /**
     * Delete the paymentGrade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentGrade : {}", id);
        paymentGradeRepository.deleteById(id);
    }
}
