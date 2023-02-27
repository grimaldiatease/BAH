package com.atease.booking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atease.booking.IntegrationTest;
import com.atease.booking.domain.PaymentGrade;
import com.atease.booking.repository.PaymentGradeRepository;
import com.atease.booking.service.dto.PaymentGradeDTO;
import com.atease.booking.service.mapper.PaymentGradeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaymentGradeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentGradeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERIES = "AAAAAAAAAA";
    private static final String UPDATED_SERIES = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-grades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentGradeRepository paymentGradeRepository;

    @Autowired
    private PaymentGradeMapper paymentGradeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentGradeMockMvc;

    private PaymentGrade paymentGrade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentGrade createEntity(EntityManager em) {
        PaymentGrade paymentGrade = new PaymentGrade().name(DEFAULT_NAME).series(DEFAULT_SERIES).description(DEFAULT_DESCRIPTION);
        return paymentGrade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentGrade createUpdatedEntity(EntityManager em) {
        PaymentGrade paymentGrade = new PaymentGrade().name(UPDATED_NAME).series(UPDATED_SERIES).description(UPDATED_DESCRIPTION);
        return paymentGrade;
    }

    @BeforeEach
    public void initTest() {
        paymentGrade = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentGrade() throws Exception {
        int databaseSizeBeforeCreate = paymentGradeRepository.findAll().size();
        // Create the PaymentGrade
        PaymentGradeDTO paymentGradeDTO = paymentGradeMapper.toDto(paymentGrade);
        restPaymentGradeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentGradeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentGrade testPaymentGrade = paymentGradeList.get(paymentGradeList.size() - 1);
        assertThat(testPaymentGrade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentGrade.getSeries()).isEqualTo(DEFAULT_SERIES);
        assertThat(testPaymentGrade.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createPaymentGradeWithExistingId() throws Exception {
        // Create the PaymentGrade with an existing ID
        paymentGrade.setId(1L);
        PaymentGradeDTO paymentGradeDTO = paymentGradeMapper.toDto(paymentGrade);

        int databaseSizeBeforeCreate = paymentGradeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentGradeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentGradeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPaymentGrades() throws Exception {
        // Initialize the database
        paymentGradeRepository.saveAndFlush(paymentGrade);

        // Get all the paymentGradeList
        restPaymentGradeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentGrade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].series").value(hasItem(DEFAULT_SERIES)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getPaymentGrade() throws Exception {
        // Initialize the database
        paymentGradeRepository.saveAndFlush(paymentGrade);

        // Get the paymentGrade
        restPaymentGradeMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentGrade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentGrade.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.series").value(DEFAULT_SERIES))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingPaymentGrade() throws Exception {
        // Get the paymentGrade
        restPaymentGradeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentGrade() throws Exception {
        // Initialize the database
        paymentGradeRepository.saveAndFlush(paymentGrade);

        int databaseSizeBeforeUpdate = paymentGradeRepository.findAll().size();

        // Update the paymentGrade
        PaymentGrade updatedPaymentGrade = paymentGradeRepository.findById(paymentGrade.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentGrade are not directly saved in db
        em.detach(updatedPaymentGrade);
        updatedPaymentGrade.name(UPDATED_NAME).series(UPDATED_SERIES).description(UPDATED_DESCRIPTION);
        PaymentGradeDTO paymentGradeDTO = paymentGradeMapper.toDto(updatedPaymentGrade);

        restPaymentGradeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentGradeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentGradeDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeUpdate);
        PaymentGrade testPaymentGrade = paymentGradeList.get(paymentGradeList.size() - 1);
        assertThat(testPaymentGrade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentGrade.getSeries()).isEqualTo(UPDATED_SERIES);
        assertThat(testPaymentGrade.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingPaymentGrade() throws Exception {
        int databaseSizeBeforeUpdate = paymentGradeRepository.findAll().size();
        paymentGrade.setId(count.incrementAndGet());

        // Create the PaymentGrade
        PaymentGradeDTO paymentGradeDTO = paymentGradeMapper.toDto(paymentGrade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentGradeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentGradeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentGradeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentGrade() throws Exception {
        int databaseSizeBeforeUpdate = paymentGradeRepository.findAll().size();
        paymentGrade.setId(count.incrementAndGet());

        // Create the PaymentGrade
        PaymentGradeDTO paymentGradeDTO = paymentGradeMapper.toDto(paymentGrade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentGradeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentGradeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentGrade() throws Exception {
        int databaseSizeBeforeUpdate = paymentGradeRepository.findAll().size();
        paymentGrade.setId(count.incrementAndGet());

        // Create the PaymentGrade
        PaymentGradeDTO paymentGradeDTO = paymentGradeMapper.toDto(paymentGrade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentGradeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentGradeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentGradeWithPatch() throws Exception {
        // Initialize the database
        paymentGradeRepository.saveAndFlush(paymentGrade);

        int databaseSizeBeforeUpdate = paymentGradeRepository.findAll().size();

        // Update the paymentGrade using partial update
        PaymentGrade partialUpdatedPaymentGrade = new PaymentGrade();
        partialUpdatedPaymentGrade.setId(paymentGrade.getId());

        partialUpdatedPaymentGrade.series(UPDATED_SERIES);

        restPaymentGradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentGrade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentGrade))
            )
            .andExpect(status().isOk());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeUpdate);
        PaymentGrade testPaymentGrade = paymentGradeList.get(paymentGradeList.size() - 1);
        assertThat(testPaymentGrade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentGrade.getSeries()).isEqualTo(UPDATED_SERIES);
        assertThat(testPaymentGrade.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdatePaymentGradeWithPatch() throws Exception {
        // Initialize the database
        paymentGradeRepository.saveAndFlush(paymentGrade);

        int databaseSizeBeforeUpdate = paymentGradeRepository.findAll().size();

        // Update the paymentGrade using partial update
        PaymentGrade partialUpdatedPaymentGrade = new PaymentGrade();
        partialUpdatedPaymentGrade.setId(paymentGrade.getId());

        partialUpdatedPaymentGrade.name(UPDATED_NAME).series(UPDATED_SERIES).description(UPDATED_DESCRIPTION);

        restPaymentGradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentGrade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentGrade))
            )
            .andExpect(status().isOk());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeUpdate);
        PaymentGrade testPaymentGrade = paymentGradeList.get(paymentGradeList.size() - 1);
        assertThat(testPaymentGrade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentGrade.getSeries()).isEqualTo(UPDATED_SERIES);
        assertThat(testPaymentGrade.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentGrade() throws Exception {
        int databaseSizeBeforeUpdate = paymentGradeRepository.findAll().size();
        paymentGrade.setId(count.incrementAndGet());

        // Create the PaymentGrade
        PaymentGradeDTO paymentGradeDTO = paymentGradeMapper.toDto(paymentGrade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentGradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentGradeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentGradeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentGrade() throws Exception {
        int databaseSizeBeforeUpdate = paymentGradeRepository.findAll().size();
        paymentGrade.setId(count.incrementAndGet());

        // Create the PaymentGrade
        PaymentGradeDTO paymentGradeDTO = paymentGradeMapper.toDto(paymentGrade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentGradeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentGradeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentGrade() throws Exception {
        int databaseSizeBeforeUpdate = paymentGradeRepository.findAll().size();
        paymentGrade.setId(count.incrementAndGet());

        // Create the PaymentGrade
        PaymentGradeDTO paymentGradeDTO = paymentGradeMapper.toDto(paymentGrade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentGradeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentGradeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentGrade in the database
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentGrade() throws Exception {
        // Initialize the database
        paymentGradeRepository.saveAndFlush(paymentGrade);

        int databaseSizeBeforeDelete = paymentGradeRepository.findAll().size();

        // Delete the paymentGrade
        restPaymentGradeMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentGrade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentGrade> paymentGradeList = paymentGradeRepository.findAll();
        assertThat(paymentGradeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
