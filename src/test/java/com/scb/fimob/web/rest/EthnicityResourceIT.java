package com.scb.fimob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.scb.fimob.IntegrationTest;
import com.scb.fimob.domain.Ethnicity;
import com.scb.fimob.repository.EthnicityRepository;
import com.scb.fimob.service.dto.EthnicityDTO;
import com.scb.fimob.service.mapper.EthnicityMapper;
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
 * Integration tests for the {@link EthnicityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EthnicityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_URDU_NAME = "AAAAAAAAAA";
    private static final String UPDATED_URDU_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ethnicities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EthnicityRepository ethnicityRepository;

    @Autowired
    private EthnicityMapper ethnicityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEthnicityMockMvc;

    private Ethnicity ethnicity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ethnicity createEntity(EntityManager em) {
        Ethnicity ethnicity = new Ethnicity().name(DEFAULT_NAME).urduName(DEFAULT_URDU_NAME);
        return ethnicity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ethnicity createUpdatedEntity(EntityManager em) {
        Ethnicity ethnicity = new Ethnicity().name(UPDATED_NAME).urduName(UPDATED_URDU_NAME);
        return ethnicity;
    }

    @BeforeEach
    public void initTest() {
        ethnicity = createEntity(em);
    }

    @Test
    @Transactional
    void createEthnicity() throws Exception {
        int databaseSizeBeforeCreate = ethnicityRepository.findAll().size();
        // Create the Ethnicity
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(ethnicity);
        restEthnicityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ethnicityDTO)))
            .andExpect(status().isCreated());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeCreate + 1);
        Ethnicity testEthnicity = ethnicityList.get(ethnicityList.size() - 1);
        assertThat(testEthnicity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEthnicity.getUrduName()).isEqualTo(DEFAULT_URDU_NAME);
    }

    @Test
    @Transactional
    void createEthnicityWithExistingId() throws Exception {
        // Create the Ethnicity with an existing ID
        ethnicity.setId(1L);
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(ethnicity);

        int databaseSizeBeforeCreate = ethnicityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEthnicityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ethnicityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ethnicityRepository.findAll().size();
        // set the field null
        ethnicity.setName(null);

        // Create the Ethnicity, which fails.
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(ethnicity);

        restEthnicityMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ethnicityDTO)))
            .andExpect(status().isBadRequest());

        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEthnicities() throws Exception {
        // Initialize the database
        ethnicityRepository.saveAndFlush(ethnicity);

        // Get all the ethnicityList
        restEthnicityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ethnicity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].urduName").value(hasItem(DEFAULT_URDU_NAME)));
    }

    @Test
    @Transactional
    void getEthnicity() throws Exception {
        // Initialize the database
        ethnicityRepository.saveAndFlush(ethnicity);

        // Get the ethnicity
        restEthnicityMockMvc
            .perform(get(ENTITY_API_URL_ID, ethnicity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ethnicity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.urduName").value(DEFAULT_URDU_NAME));
    }

    @Test
    @Transactional
    void getNonExistingEthnicity() throws Exception {
        // Get the ethnicity
        restEthnicityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEthnicity() throws Exception {
        // Initialize the database
        ethnicityRepository.saveAndFlush(ethnicity);

        int databaseSizeBeforeUpdate = ethnicityRepository.findAll().size();

        // Update the ethnicity
        Ethnicity updatedEthnicity = ethnicityRepository.findById(ethnicity.getId()).get();
        // Disconnect from session so that the updates on updatedEthnicity are not directly saved in db
        em.detach(updatedEthnicity);
        updatedEthnicity.name(UPDATED_NAME).urduName(UPDATED_URDU_NAME);
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(updatedEthnicity);

        restEthnicityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ethnicityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ethnicityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeUpdate);
        Ethnicity testEthnicity = ethnicityList.get(ethnicityList.size() - 1);
        assertThat(testEthnicity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEthnicity.getUrduName()).isEqualTo(UPDATED_URDU_NAME);
    }

    @Test
    @Transactional
    void putNonExistingEthnicity() throws Exception {
        int databaseSizeBeforeUpdate = ethnicityRepository.findAll().size();
        ethnicity.setId(count.incrementAndGet());

        // Create the Ethnicity
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(ethnicity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEthnicityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ethnicityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ethnicityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEthnicity() throws Exception {
        int databaseSizeBeforeUpdate = ethnicityRepository.findAll().size();
        ethnicity.setId(count.incrementAndGet());

        // Create the Ethnicity
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(ethnicity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEthnicityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ethnicityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEthnicity() throws Exception {
        int databaseSizeBeforeUpdate = ethnicityRepository.findAll().size();
        ethnicity.setId(count.incrementAndGet());

        // Create the Ethnicity
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(ethnicity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEthnicityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ethnicityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEthnicityWithPatch() throws Exception {
        // Initialize the database
        ethnicityRepository.saveAndFlush(ethnicity);

        int databaseSizeBeforeUpdate = ethnicityRepository.findAll().size();

        // Update the ethnicity using partial update
        Ethnicity partialUpdatedEthnicity = new Ethnicity();
        partialUpdatedEthnicity.setId(ethnicity.getId());

        partialUpdatedEthnicity.name(UPDATED_NAME).urduName(UPDATED_URDU_NAME);

        restEthnicityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEthnicity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEthnicity))
            )
            .andExpect(status().isOk());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeUpdate);
        Ethnicity testEthnicity = ethnicityList.get(ethnicityList.size() - 1);
        assertThat(testEthnicity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEthnicity.getUrduName()).isEqualTo(UPDATED_URDU_NAME);
    }

    @Test
    @Transactional
    void fullUpdateEthnicityWithPatch() throws Exception {
        // Initialize the database
        ethnicityRepository.saveAndFlush(ethnicity);

        int databaseSizeBeforeUpdate = ethnicityRepository.findAll().size();

        // Update the ethnicity using partial update
        Ethnicity partialUpdatedEthnicity = new Ethnicity();
        partialUpdatedEthnicity.setId(ethnicity.getId());

        partialUpdatedEthnicity.name(UPDATED_NAME).urduName(UPDATED_URDU_NAME);

        restEthnicityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEthnicity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEthnicity))
            )
            .andExpect(status().isOk());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeUpdate);
        Ethnicity testEthnicity = ethnicityList.get(ethnicityList.size() - 1);
        assertThat(testEthnicity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEthnicity.getUrduName()).isEqualTo(UPDATED_URDU_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingEthnicity() throws Exception {
        int databaseSizeBeforeUpdate = ethnicityRepository.findAll().size();
        ethnicity.setId(count.incrementAndGet());

        // Create the Ethnicity
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(ethnicity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEthnicityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ethnicityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ethnicityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEthnicity() throws Exception {
        int databaseSizeBeforeUpdate = ethnicityRepository.findAll().size();
        ethnicity.setId(count.incrementAndGet());

        // Create the Ethnicity
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(ethnicity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEthnicityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ethnicityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEthnicity() throws Exception {
        int databaseSizeBeforeUpdate = ethnicityRepository.findAll().size();
        ethnicity.setId(count.incrementAndGet());

        // Create the Ethnicity
        EthnicityDTO ethnicityDTO = ethnicityMapper.toDto(ethnicity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEthnicityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ethnicityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ethnicity in the database
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEthnicity() throws Exception {
        // Initialize the database
        ethnicityRepository.saveAndFlush(ethnicity);

        int databaseSizeBeforeDelete = ethnicityRepository.findAll().size();

        // Delete the ethnicity
        restEthnicityMockMvc
            .perform(delete(ENTITY_API_URL_ID, ethnicity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ethnicity> ethnicityList = ethnicityRepository.findAll();
        assertThat(ethnicityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
