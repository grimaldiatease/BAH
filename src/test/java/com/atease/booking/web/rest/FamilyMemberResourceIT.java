package com.atease.booking.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.atease.booking.IntegrationTest;
import com.atease.booking.domain.FamilyMember;
import com.atease.booking.domain.enumeration.Relation;
import com.atease.booking.repository.FamilyMemberRepository;
import com.atease.booking.service.dto.FamilyMemberDTO;
import com.atease.booking.service.mapper.FamilyMemberMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link FamilyMemberResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FamilyMemberResourceIT {

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DOB = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DOB = LocalDate.now(ZoneId.systemDefault());

    private static final Relation DEFAULT_RELATION = Relation.SON;
    private static final Relation UPDATED_RELATION = Relation.WIFE;

    private static final String ENTITY_API_URL = "/api/family-members";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FamilyMemberRepository familyMemberRepository;

    @Autowired
    private FamilyMemberMapper familyMemberMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilyMemberMockMvc;

    private FamilyMember familyMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyMember createEntity(EntityManager em) {
        FamilyMember familyMember = new FamilyMember().fullname(DEFAULT_FULLNAME).dob(DEFAULT_DOB).relation(DEFAULT_RELATION);
        return familyMember;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FamilyMember createUpdatedEntity(EntityManager em) {
        FamilyMember familyMember = new FamilyMember().fullname(UPDATED_FULLNAME).dob(UPDATED_DOB).relation(UPDATED_RELATION);
        return familyMember;
    }

    @BeforeEach
    public void initTest() {
        familyMember = createEntity(em);
    }

    @Test
    @Transactional
    void createFamilyMember() throws Exception {
        int databaseSizeBeforeCreate = familyMemberRepository.findAll().size();
        // Create the FamilyMember
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);
        restFamilyMemberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyMemberDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeCreate + 1);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);
        assertThat(testFamilyMember.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testFamilyMember.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testFamilyMember.getRelation()).isEqualTo(DEFAULT_RELATION);
    }

    @Test
    @Transactional
    void createFamilyMemberWithExistingId() throws Exception {
        // Create the FamilyMember with an existing ID
        familyMember.setId(1L);
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);

        int databaseSizeBeforeCreate = familyMemberRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilyMemberMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFamilyMembers() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        // Get all the familyMemberList
        restFamilyMemberMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(familyMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME)))
            .andExpect(jsonPath("$.[*].dob").value(hasItem(DEFAULT_DOB.toString())))
            .andExpect(jsonPath("$.[*].relation").value(hasItem(DEFAULT_RELATION.toString())));
    }

    @Test
    @Transactional
    void getFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        // Get the familyMember
        restFamilyMemberMockMvc
            .perform(get(ENTITY_API_URL_ID, familyMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(familyMember.getId().intValue()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME))
            .andExpect(jsonPath("$.dob").value(DEFAULT_DOB.toString()))
            .andExpect(jsonPath("$.relation").value(DEFAULT_RELATION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFamilyMember() throws Exception {
        // Get the familyMember
        restFamilyMemberMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();

        // Update the familyMember
        FamilyMember updatedFamilyMember = familyMemberRepository.findById(familyMember.getId()).get();
        // Disconnect from session so that the updates on updatedFamilyMember are not directly saved in db
        em.detach(updatedFamilyMember);
        updatedFamilyMember.fullname(UPDATED_FULLNAME).dob(UPDATED_DOB).relation(UPDATED_RELATION);
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(updatedFamilyMember);

        restFamilyMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, familyMemberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO))
            )
            .andExpect(status().isOk());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);
        assertThat(testFamilyMember.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testFamilyMember.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testFamilyMember.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    @Transactional
    void putNonExistingFamilyMember() throws Exception {
        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();
        familyMember.setId(count.incrementAndGet());

        // Create the FamilyMember
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, familyMemberDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFamilyMember() throws Exception {
        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();
        familyMember.setId(count.incrementAndGet());

        // Create the FamilyMember
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyMemberMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFamilyMember() throws Exception {
        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();
        familyMember.setId(count.incrementAndGet());

        // Create the FamilyMember
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyMemberMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(familyMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFamilyMemberWithPatch() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();

        // Update the familyMember using partial update
        FamilyMember partialUpdatedFamilyMember = new FamilyMember();
        partialUpdatedFamilyMember.setId(familyMember.getId());

        partialUpdatedFamilyMember.fullname(UPDATED_FULLNAME).relation(UPDATED_RELATION);

        restFamilyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFamilyMember))
            )
            .andExpect(status().isOk());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);
        assertThat(testFamilyMember.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testFamilyMember.getDob()).isEqualTo(DEFAULT_DOB);
        assertThat(testFamilyMember.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    @Transactional
    void fullUpdateFamilyMemberWithPatch() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();

        // Update the familyMember using partial update
        FamilyMember partialUpdatedFamilyMember = new FamilyMember();
        partialUpdatedFamilyMember.setId(familyMember.getId());

        partialUpdatedFamilyMember.fullname(UPDATED_FULLNAME).dob(UPDATED_DOB).relation(UPDATED_RELATION);

        restFamilyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamilyMember.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFamilyMember))
            )
            .andExpect(status().isOk());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
        FamilyMember testFamilyMember = familyMemberList.get(familyMemberList.size() - 1);
        assertThat(testFamilyMember.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testFamilyMember.getDob()).isEqualTo(UPDATED_DOB);
        assertThat(testFamilyMember.getRelation()).isEqualTo(UPDATED_RELATION);
    }

    @Test
    @Transactional
    void patchNonExistingFamilyMember() throws Exception {
        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();
        familyMember.setId(count.incrementAndGet());

        // Create the FamilyMember
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, familyMemberDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFamilyMember() throws Exception {
        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();
        familyMember.setId(count.incrementAndGet());

        // Create the FamilyMember
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFamilyMember() throws Exception {
        int databaseSizeBeforeUpdate = familyMemberRepository.findAll().size();
        familyMember.setId(count.incrementAndGet());

        // Create the FamilyMember
        FamilyMemberDTO familyMemberDTO = familyMemberMapper.toDto(familyMember);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilyMemberMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(familyMemberDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FamilyMember in the database
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFamilyMember() throws Exception {
        // Initialize the database
        familyMemberRepository.saveAndFlush(familyMember);

        int databaseSizeBeforeDelete = familyMemberRepository.findAll().size();

        // Delete the familyMember
        restFamilyMemberMockMvc
            .perform(delete(ENTITY_API_URL_ID, familyMember.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FamilyMember> familyMemberList = familyMemberRepository.findAll();
        assertThat(familyMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
