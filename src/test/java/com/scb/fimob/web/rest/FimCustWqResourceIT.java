package com.scb.fimob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.scb.fimob.IntegrationTest;
import com.scb.fimob.domain.FimCustWq;
import com.scb.fimob.repository.FimCustWqRepository;
import com.scb.fimob.service.dto.FimCustWqDTO;
import com.scb.fimob.service.mapper.FimCustWqMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link FimCustWqResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FimCustWqResourceIT {

    private static final String DEFAULT_CUST_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUST_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ID_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ID_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CTRY_CODE = "AAA";
    private static final String UPDATED_CTRY_CODE = "BBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBB";

    private static final Instant DEFAULT_CREATED_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RECORD_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_RECORD_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_UPLOAD_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_REMARK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fim-cust-wqs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FimCustWqRepository fimCustWqRepository;

    @Autowired
    private FimCustWqMapper fimCustWqMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFimCustWqMockMvc;

    private FimCustWq fimCustWq;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimCustWq createEntity(EntityManager em) {
        FimCustWq fimCustWq = new FimCustWq()
            .custId(DEFAULT_CUST_ID)
            .clientId(DEFAULT_CLIENT_ID)
            .idType(DEFAULT_ID_TYPE)
            .ctryCode(DEFAULT_CTRY_CODE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdTs(DEFAULT_CREATED_TS)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTs(DEFAULT_UPDATED_TS)
            .recordStatus(DEFAULT_RECORD_STATUS)
            .uploadRemark(DEFAULT_UPLOAD_REMARK);
        return fimCustWq;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimCustWq createUpdatedEntity(EntityManager em) {
        FimCustWq fimCustWq = new FimCustWq()
            .custId(UPDATED_CUST_ID)
            .clientId(UPDATED_CLIENT_ID)
            .idType(UPDATED_ID_TYPE)
            .ctryCode(UPDATED_CTRY_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);
        return fimCustWq;
    }

    @BeforeEach
    public void initTest() {
        fimCustWq = createEntity(em);
    }

    @Test
    @Transactional
    void createFimCustWq() throws Exception {
        int databaseSizeBeforeCreate = fimCustWqRepository.findAll().size();
        // Create the FimCustWq
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);
        restFimCustWqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO)))
            .andExpect(status().isCreated());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeCreate + 1);
        FimCustWq testFimCustWq = fimCustWqList.get(fimCustWqList.size() - 1);
        assertThat(testFimCustWq.getCustId()).isEqualTo(DEFAULT_CUST_ID);
        assertThat(testFimCustWq.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testFimCustWq.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testFimCustWq.getCtryCode()).isEqualTo(DEFAULT_CTRY_CODE);
        assertThat(testFimCustWq.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFimCustWq.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimCustWq.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimCustWq.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testFimCustWq.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
        assertThat(testFimCustWq.getUploadRemark()).isEqualTo(DEFAULT_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void createFimCustWqWithExistingId() throws Exception {
        // Create the FimCustWq with an existing ID
        fimCustWq.setId(1L);
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        int databaseSizeBeforeCreate = fimCustWqRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFimCustWqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimCustWqRepository.findAll().size();
        // set the field null
        fimCustWq.setClientId(null);

        // Create the FimCustWq, which fails.
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        restFimCustWqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO)))
            .andExpect(status().isBadRequest());

        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimCustWqRepository.findAll().size();
        // set the field null
        fimCustWq.setIdType(null);

        // Create the FimCustWq, which fails.
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        restFimCustWqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO)))
            .andExpect(status().isBadRequest());

        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCtryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimCustWqRepository.findAll().size();
        // set the field null
        fimCustWq.setCtryCode(null);

        // Create the FimCustWq, which fails.
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        restFimCustWqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO)))
            .andExpect(status().isBadRequest());

        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimCustWqRepository.findAll().size();
        // set the field null
        fimCustWq.setCreatedBy(null);

        // Create the FimCustWq, which fails.
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        restFimCustWqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO)))
            .andExpect(status().isBadRequest());

        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFimCustWqs() throws Exception {
        // Initialize the database
        fimCustWqRepository.saveAndFlush(fimCustWq);

        // Get all the fimCustWqList
        restFimCustWqMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fimCustWq.getId().intValue())))
            .andExpect(jsonPath("$.[*].custId").value(hasItem(DEFAULT_CUST_ID)))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID)))
            .andExpect(jsonPath("$.[*].idType").value(hasItem(DEFAULT_ID_TYPE)))
            .andExpect(jsonPath("$.[*].ctryCode").value(hasItem(DEFAULT_CTRY_CODE)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdTs").value(hasItem(DEFAULT_CREATED_TS.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTs").value(hasItem(DEFAULT_UPDATED_TS.toString())))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS)))
            .andExpect(jsonPath("$.[*].uploadRemark").value(hasItem(DEFAULT_UPLOAD_REMARK)));
    }

    @Test
    @Transactional
    void getFimCustWq() throws Exception {
        // Initialize the database
        fimCustWqRepository.saveAndFlush(fimCustWq);

        // Get the fimCustWq
        restFimCustWqMockMvc
            .perform(get(ENTITY_API_URL_ID, fimCustWq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fimCustWq.getId().intValue()))
            .andExpect(jsonPath("$.custId").value(DEFAULT_CUST_ID))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID))
            .andExpect(jsonPath("$.idType").value(DEFAULT_ID_TYPE))
            .andExpect(jsonPath("$.ctryCode").value(DEFAULT_CTRY_CODE))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdTs").value(DEFAULT_CREATED_TS.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTs").value(DEFAULT_UPDATED_TS.toString()))
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS))
            .andExpect(jsonPath("$.uploadRemark").value(DEFAULT_UPLOAD_REMARK));
    }

    @Test
    @Transactional
    void getNonExistingFimCustWq() throws Exception {
        // Get the fimCustWq
        restFimCustWqMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFimCustWq() throws Exception {
        // Initialize the database
        fimCustWqRepository.saveAndFlush(fimCustWq);

        int databaseSizeBeforeUpdate = fimCustWqRepository.findAll().size();

        // Update the fimCustWq
        FimCustWq updatedFimCustWq = fimCustWqRepository.findById(fimCustWq.getId()).get();
        // Disconnect from session so that the updates on updatedFimCustWq are not directly saved in db
        em.detach(updatedFimCustWq);
        updatedFimCustWq
            .custId(UPDATED_CUST_ID)
            .clientId(UPDATED_CLIENT_ID)
            .idType(UPDATED_ID_TYPE)
            .ctryCode(UPDATED_CTRY_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(updatedFimCustWq);

        restFimCustWqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimCustWqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO))
            )
            .andExpect(status().isOk());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeUpdate);
        FimCustWq testFimCustWq = fimCustWqList.get(fimCustWqList.size() - 1);
        assertThat(testFimCustWq.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimCustWq.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testFimCustWq.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testFimCustWq.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimCustWq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimCustWq.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimCustWq.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimCustWq.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimCustWq.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testFimCustWq.getUploadRemark()).isEqualTo(UPDATED_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void putNonExistingFimCustWq() throws Exception {
        int databaseSizeBeforeUpdate = fimCustWqRepository.findAll().size();
        fimCustWq.setId(count.incrementAndGet());

        // Create the FimCustWq
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimCustWqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimCustWqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFimCustWq() throws Exception {
        int databaseSizeBeforeUpdate = fimCustWqRepository.findAll().size();
        fimCustWq.setId(count.incrementAndGet());

        // Create the FimCustWq
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimCustWqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFimCustWq() throws Exception {
        int databaseSizeBeforeUpdate = fimCustWqRepository.findAll().size();
        fimCustWq.setId(count.incrementAndGet());

        // Create the FimCustWq
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimCustWqMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFimCustWqWithPatch() throws Exception {
        // Initialize the database
        fimCustWqRepository.saveAndFlush(fimCustWq);

        int databaseSizeBeforeUpdate = fimCustWqRepository.findAll().size();

        // Update the fimCustWq using partial update
        FimCustWq partialUpdatedFimCustWq = new FimCustWq();
        partialUpdatedFimCustWq.setId(fimCustWq.getId());

        partialUpdatedFimCustWq
            .custId(UPDATED_CUST_ID)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS);

        restFimCustWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimCustWq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimCustWq))
            )
            .andExpect(status().isOk());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeUpdate);
        FimCustWq testFimCustWq = fimCustWqList.get(fimCustWqList.size() - 1);
        assertThat(testFimCustWq.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimCustWq.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testFimCustWq.getIdType()).isEqualTo(DEFAULT_ID_TYPE);
        assertThat(testFimCustWq.getCtryCode()).isEqualTo(DEFAULT_CTRY_CODE);
        assertThat(testFimCustWq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimCustWq.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimCustWq.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimCustWq.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimCustWq.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testFimCustWq.getUploadRemark()).isEqualTo(DEFAULT_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void fullUpdateFimCustWqWithPatch() throws Exception {
        // Initialize the database
        fimCustWqRepository.saveAndFlush(fimCustWq);

        int databaseSizeBeforeUpdate = fimCustWqRepository.findAll().size();

        // Update the fimCustWq using partial update
        FimCustWq partialUpdatedFimCustWq = new FimCustWq();
        partialUpdatedFimCustWq.setId(fimCustWq.getId());

        partialUpdatedFimCustWq
            .custId(UPDATED_CUST_ID)
            .clientId(UPDATED_CLIENT_ID)
            .idType(UPDATED_ID_TYPE)
            .ctryCode(UPDATED_CTRY_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);

        restFimCustWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimCustWq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimCustWq))
            )
            .andExpect(status().isOk());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeUpdate);
        FimCustWq testFimCustWq = fimCustWqList.get(fimCustWqList.size() - 1);
        assertThat(testFimCustWq.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimCustWq.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testFimCustWq.getIdType()).isEqualTo(UPDATED_ID_TYPE);
        assertThat(testFimCustWq.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimCustWq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimCustWq.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimCustWq.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimCustWq.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimCustWq.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testFimCustWq.getUploadRemark()).isEqualTo(UPDATED_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void patchNonExistingFimCustWq() throws Exception {
        int databaseSizeBeforeUpdate = fimCustWqRepository.findAll().size();
        fimCustWq.setId(count.incrementAndGet());

        // Create the FimCustWq
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimCustWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fimCustWqDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFimCustWq() throws Exception {
        int databaseSizeBeforeUpdate = fimCustWqRepository.findAll().size();
        fimCustWq.setId(count.incrementAndGet());

        // Create the FimCustWq
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimCustWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFimCustWq() throws Exception {
        int databaseSizeBeforeUpdate = fimCustWqRepository.findAll().size();
        fimCustWq.setId(count.incrementAndGet());

        // Create the FimCustWq
        FimCustWqDTO fimCustWqDTO = fimCustWqMapper.toDto(fimCustWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimCustWqMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fimCustWqDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimCustWq in the database
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFimCustWq() throws Exception {
        // Initialize the database
        fimCustWqRepository.saveAndFlush(fimCustWq);

        int databaseSizeBeforeDelete = fimCustWqRepository.findAll().size();

        // Delete the fimCustWq
        restFimCustWqMockMvc
            .perform(delete(ENTITY_API_URL_ID, fimCustWq.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FimCustWq> fimCustWqList = fimCustWqRepository.findAll();
        assertThat(fimCustWqList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
