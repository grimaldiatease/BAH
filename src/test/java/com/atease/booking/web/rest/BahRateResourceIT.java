package com.atease.booking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atease.booking.IntegrationTest;
import com.atease.booking.domain.BahRate;
import com.atease.booking.repository.BahRateRepository;
import com.atease.booking.service.dto.BahRateDTO;
import com.atease.booking.service.mapper.BahRateMapper;
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
 * Integration tests for the {@link BahRateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BahRateResourceIT {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bah-rates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BahRateRepository bahRateRepository;

    @Autowired
    private BahRateMapper bahRateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBahRateMockMvc;

    private BahRate bahRate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BahRate createEntity(EntityManager em) {
        BahRate bahRate = new BahRate().amount(DEFAULT_AMOUNT).name(DEFAULT_NAME);
        return bahRate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BahRate createUpdatedEntity(EntityManager em) {
        BahRate bahRate = new BahRate().amount(UPDATED_AMOUNT).name(UPDATED_NAME);
        return bahRate;
    }

    @BeforeEach
    public void initTest() {
        bahRate = createEntity(em);
    }

    @Test
    @Transactional
    void createBahRate() throws Exception {
        int databaseSizeBeforeCreate = bahRateRepository.findAll().size();
        // Create the BahRate
        BahRateDTO bahRateDTO = bahRateMapper.toDto(bahRate);
        restBahRateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bahRateDTO)))
            .andExpect(status().isCreated());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeCreate + 1);
        BahRate testBahRate = bahRateList.get(bahRateList.size() - 1);
        assertThat(testBahRate.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBahRate.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createBahRateWithExistingId() throws Exception {
        // Create the BahRate with an existing ID
        bahRate.setId(1L);
        BahRateDTO bahRateDTO = bahRateMapper.toDto(bahRate);

        int databaseSizeBeforeCreate = bahRateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBahRateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bahRateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBahRates() throws Exception {
        // Initialize the database
        bahRateRepository.saveAndFlush(bahRate);

        // Get all the bahRateList
        restBahRateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bahRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getBahRate() throws Exception {
        // Initialize the database
        bahRateRepository.saveAndFlush(bahRate);

        // Get the bahRate
        restBahRateMockMvc
            .perform(get(ENTITY_API_URL_ID, bahRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bahRate.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingBahRate() throws Exception {
        // Get the bahRate
        restBahRateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBahRate() throws Exception {
        // Initialize the database
        bahRateRepository.saveAndFlush(bahRate);

        int databaseSizeBeforeUpdate = bahRateRepository.findAll().size();

        // Update the bahRate
        BahRate updatedBahRate = bahRateRepository.findById(bahRate.getId()).get();
        // Disconnect from session so that the updates on updatedBahRate are not directly saved in db
        em.detach(updatedBahRate);
        updatedBahRate.amount(UPDATED_AMOUNT).name(UPDATED_NAME);
        BahRateDTO bahRateDTO = bahRateMapper.toDto(updatedBahRate);

        restBahRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bahRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bahRateDTO))
            )
            .andExpect(status().isOk());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeUpdate);
        BahRate testBahRate = bahRateList.get(bahRateList.size() - 1);
        assertThat(testBahRate.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBahRate.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingBahRate() throws Exception {
        int databaseSizeBeforeUpdate = bahRateRepository.findAll().size();
        bahRate.setId(count.incrementAndGet());

        // Create the BahRate
        BahRateDTO bahRateDTO = bahRateMapper.toDto(bahRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBahRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bahRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bahRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBahRate() throws Exception {
        int databaseSizeBeforeUpdate = bahRateRepository.findAll().size();
        bahRate.setId(count.incrementAndGet());

        // Create the BahRate
        BahRateDTO bahRateDTO = bahRateMapper.toDto(bahRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBahRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bahRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBahRate() throws Exception {
        int databaseSizeBeforeUpdate = bahRateRepository.findAll().size();
        bahRate.setId(count.incrementAndGet());

        // Create the BahRate
        BahRateDTO bahRateDTO = bahRateMapper.toDto(bahRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBahRateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bahRateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBahRateWithPatch() throws Exception {
        // Initialize the database
        bahRateRepository.saveAndFlush(bahRate);

        int databaseSizeBeforeUpdate = bahRateRepository.findAll().size();

        // Update the bahRate using partial update
        BahRate partialUpdatedBahRate = new BahRate();
        partialUpdatedBahRate.setId(bahRate.getId());

        partialUpdatedBahRate.name(UPDATED_NAME);

        restBahRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBahRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBahRate))
            )
            .andExpect(status().isOk());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeUpdate);
        BahRate testBahRate = bahRateList.get(bahRateList.size() - 1);
        assertThat(testBahRate.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBahRate.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateBahRateWithPatch() throws Exception {
        // Initialize the database
        bahRateRepository.saveAndFlush(bahRate);

        int databaseSizeBeforeUpdate = bahRateRepository.findAll().size();

        // Update the bahRate using partial update
        BahRate partialUpdatedBahRate = new BahRate();
        partialUpdatedBahRate.setId(bahRate.getId());

        partialUpdatedBahRate.amount(UPDATED_AMOUNT).name(UPDATED_NAME);

        restBahRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBahRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBahRate))
            )
            .andExpect(status().isOk());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeUpdate);
        BahRate testBahRate = bahRateList.get(bahRateList.size() - 1);
        assertThat(testBahRate.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBahRate.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingBahRate() throws Exception {
        int databaseSizeBeforeUpdate = bahRateRepository.findAll().size();
        bahRate.setId(count.incrementAndGet());

        // Create the BahRate
        BahRateDTO bahRateDTO = bahRateMapper.toDto(bahRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBahRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bahRateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bahRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBahRate() throws Exception {
        int databaseSizeBeforeUpdate = bahRateRepository.findAll().size();
        bahRate.setId(count.incrementAndGet());

        // Create the BahRate
        BahRateDTO bahRateDTO = bahRateMapper.toDto(bahRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBahRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bahRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBahRate() throws Exception {
        int databaseSizeBeforeUpdate = bahRateRepository.findAll().size();
        bahRate.setId(count.incrementAndGet());

        // Create the BahRate
        BahRateDTO bahRateDTO = bahRateMapper.toDto(bahRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBahRateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bahRateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BahRate in the database
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBahRate() throws Exception {
        // Initialize the database
        bahRateRepository.saveAndFlush(bahRate);

        int databaseSizeBeforeDelete = bahRateRepository.findAll().size();

        // Delete the bahRate
        restBahRateMockMvc
            .perform(delete(ENTITY_API_URL_ID, bahRate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BahRate> bahRateList = bahRateRepository.findAll();
        assertThat(bahRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
