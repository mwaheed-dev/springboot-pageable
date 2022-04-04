package com.scb.fimob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.scb.fimob.IntegrationTest;
import com.scb.fimob.domain.FimSettAcctWq;
import com.scb.fimob.repository.FimSettAcctWqRepository;
import com.scb.fimob.service.dto.FimSettAcctWqDTO;
import com.scb.fimob.service.mapper.FimSettAcctWqMapper;
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
 * Integration tests for the {@link FimSettAcctWqResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FimSettAcctWqResourceIT {

    private static final String DEFAULT_SETTACC_ID = "AAAAAAAAAA";
    private static final String UPDATED_SETTACC_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SETT_ACCT_NBR = "AAAAAAAAAA";
    private static final String UPDATED_SETT_ACCT_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_SETT_CCY = "AAA";
    private static final String UPDATED_SETT_CCY = "BBB";

    private static final String DEFAULT_SETT_ACCT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_SETT_ACCT_STATUS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/fim-sett-acct-wqs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FimSettAcctWqRepository fimSettAcctWqRepository;

    @Autowired
    private FimSettAcctWqMapper fimSettAcctWqMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFimSettAcctWqMockMvc;

    private FimSettAcctWq fimSettAcctWq;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimSettAcctWq createEntity(EntityManager em) {
        FimSettAcctWq fimSettAcctWq = new FimSettAcctWq()
            .settaccId(DEFAULT_SETTACC_ID)
            .accountId(DEFAULT_ACCOUNT_ID)
            .settAcctNbr(DEFAULT_SETT_ACCT_NBR)
            .settCcy(DEFAULT_SETT_CCY)
            .settAcctStatus(DEFAULT_SETT_ACCT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdTs(DEFAULT_CREATED_TS)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTs(DEFAULT_UPDATED_TS)
            .recordStatus(DEFAULT_RECORD_STATUS)
            .uploadRemark(DEFAULT_UPLOAD_REMARK);
        return fimSettAcctWq;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimSettAcctWq createUpdatedEntity(EntityManager em) {
        FimSettAcctWq fimSettAcctWq = new FimSettAcctWq()
            .settaccId(UPDATED_SETTACC_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .settAcctNbr(UPDATED_SETT_ACCT_NBR)
            .settCcy(UPDATED_SETT_CCY)
            .settAcctStatus(UPDATED_SETT_ACCT_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);
        return fimSettAcctWq;
    }

    @BeforeEach
    public void initTest() {
        fimSettAcctWq = createEntity(em);
    }

    @Test
    @Transactional
    void createFimSettAcctWq() throws Exception {
        int databaseSizeBeforeCreate = fimSettAcctWqRepository.findAll().size();
        // Create the FimSettAcctWq
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);
        restFimSettAcctWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeCreate + 1);
        FimSettAcctWq testFimSettAcctWq = fimSettAcctWqList.get(fimSettAcctWqList.size() - 1);
        assertThat(testFimSettAcctWq.getSettaccId()).isEqualTo(DEFAULT_SETTACC_ID);
        assertThat(testFimSettAcctWq.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testFimSettAcctWq.getSettAcctNbr()).isEqualTo(DEFAULT_SETT_ACCT_NBR);
        assertThat(testFimSettAcctWq.getSettCcy()).isEqualTo(DEFAULT_SETT_CCY);
        assertThat(testFimSettAcctWq.getSettAcctStatus()).isEqualTo(DEFAULT_SETT_ACCT_STATUS);
        assertThat(testFimSettAcctWq.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFimSettAcctWq.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimSettAcctWq.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimSettAcctWq.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testFimSettAcctWq.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
        assertThat(testFimSettAcctWq.getUploadRemark()).isEqualTo(DEFAULT_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void createFimSettAcctWqWithExistingId() throws Exception {
        // Create the FimSettAcctWq with an existing ID
        fimSettAcctWq.setId(1L);
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        int databaseSizeBeforeCreate = fimSettAcctWqRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFimSettAcctWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccountIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimSettAcctWqRepository.findAll().size();
        // set the field null
        fimSettAcctWq.setAccountId(null);

        // Create the FimSettAcctWq, which fails.
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        restFimSettAcctWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimSettAcctWqRepository.findAll().size();
        // set the field null
        fimSettAcctWq.setSettAcctNbr(null);

        // Create the FimSettAcctWq, which fails.
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        restFimSettAcctWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettCcyIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimSettAcctWqRepository.findAll().size();
        // set the field null
        fimSettAcctWq.setSettCcy(null);

        // Create the FimSettAcctWq, which fails.
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        restFimSettAcctWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettAcctStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimSettAcctWqRepository.findAll().size();
        // set the field null
        fimSettAcctWq.setSettAcctStatus(null);

        // Create the FimSettAcctWq, which fails.
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        restFimSettAcctWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFimSettAcctWqs() throws Exception {
        // Initialize the database
        fimSettAcctWqRepository.saveAndFlush(fimSettAcctWq);

        // Get all the fimSettAcctWqList
        restFimSettAcctWqMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fimSettAcctWq.getId().intValue())))
            .andExpect(jsonPath("$.[*].settaccId").value(hasItem(DEFAULT_SETTACC_ID)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].settAcctNbr").value(hasItem(DEFAULT_SETT_ACCT_NBR)))
            .andExpect(jsonPath("$.[*].settCcy").value(hasItem(DEFAULT_SETT_CCY)))
            .andExpect(jsonPath("$.[*].settAcctStatus").value(hasItem(DEFAULT_SETT_ACCT_STATUS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdTs").value(hasItem(DEFAULT_CREATED_TS.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTs").value(hasItem(DEFAULT_UPDATED_TS.toString())))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS)))
            .andExpect(jsonPath("$.[*].uploadRemark").value(hasItem(DEFAULT_UPLOAD_REMARK)));
    }

    @Test
    @Transactional
    void getFimSettAcctWq() throws Exception {
        // Initialize the database
        fimSettAcctWqRepository.saveAndFlush(fimSettAcctWq);

        // Get the fimSettAcctWq
        restFimSettAcctWqMockMvc
            .perform(get(ENTITY_API_URL_ID, fimSettAcctWq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fimSettAcctWq.getId().intValue()))
            .andExpect(jsonPath("$.settaccId").value(DEFAULT_SETTACC_ID))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.settAcctNbr").value(DEFAULT_SETT_ACCT_NBR))
            .andExpect(jsonPath("$.settCcy").value(DEFAULT_SETT_CCY))
            .andExpect(jsonPath("$.settAcctStatus").value(DEFAULT_SETT_ACCT_STATUS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdTs").value(DEFAULT_CREATED_TS.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTs").value(DEFAULT_UPDATED_TS.toString()))
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS))
            .andExpect(jsonPath("$.uploadRemark").value(DEFAULT_UPLOAD_REMARK));
    }

    @Test
    @Transactional
    void getNonExistingFimSettAcctWq() throws Exception {
        // Get the fimSettAcctWq
        restFimSettAcctWqMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFimSettAcctWq() throws Exception {
        // Initialize the database
        fimSettAcctWqRepository.saveAndFlush(fimSettAcctWq);

        int databaseSizeBeforeUpdate = fimSettAcctWqRepository.findAll().size();

        // Update the fimSettAcctWq
        FimSettAcctWq updatedFimSettAcctWq = fimSettAcctWqRepository.findById(fimSettAcctWq.getId()).get();
        // Disconnect from session so that the updates on updatedFimSettAcctWq are not directly saved in db
        em.detach(updatedFimSettAcctWq);
        updatedFimSettAcctWq
            .settaccId(UPDATED_SETTACC_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .settAcctNbr(UPDATED_SETT_ACCT_NBR)
            .settCcy(UPDATED_SETT_CCY)
            .settAcctStatus(UPDATED_SETT_ACCT_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(updatedFimSettAcctWq);

        restFimSettAcctWqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimSettAcctWqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isOk());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeUpdate);
        FimSettAcctWq testFimSettAcctWq = fimSettAcctWqList.get(fimSettAcctWqList.size() - 1);
        assertThat(testFimSettAcctWq.getSettaccId()).isEqualTo(UPDATED_SETTACC_ID);
        assertThat(testFimSettAcctWq.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimSettAcctWq.getSettAcctNbr()).isEqualTo(UPDATED_SETT_ACCT_NBR);
        assertThat(testFimSettAcctWq.getSettCcy()).isEqualTo(UPDATED_SETT_CCY);
        assertThat(testFimSettAcctWq.getSettAcctStatus()).isEqualTo(UPDATED_SETT_ACCT_STATUS);
        assertThat(testFimSettAcctWq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimSettAcctWq.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimSettAcctWq.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimSettAcctWq.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimSettAcctWq.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testFimSettAcctWq.getUploadRemark()).isEqualTo(UPDATED_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void putNonExistingFimSettAcctWq() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctWqRepository.findAll().size();
        fimSettAcctWq.setId(count.incrementAndGet());

        // Create the FimSettAcctWq
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimSettAcctWqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimSettAcctWqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFimSettAcctWq() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctWqRepository.findAll().size();
        fimSettAcctWq.setId(count.incrementAndGet());

        // Create the FimSettAcctWq
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimSettAcctWqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFimSettAcctWq() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctWqRepository.findAll().size();
        fimSettAcctWq.setId(count.incrementAndGet());

        // Create the FimSettAcctWq
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimSettAcctWqMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFimSettAcctWqWithPatch() throws Exception {
        // Initialize the database
        fimSettAcctWqRepository.saveAndFlush(fimSettAcctWq);

        int databaseSizeBeforeUpdate = fimSettAcctWqRepository.findAll().size();

        // Update the fimSettAcctWq using partial update
        FimSettAcctWq partialUpdatedFimSettAcctWq = new FimSettAcctWq();
        partialUpdatedFimSettAcctWq.setId(fimSettAcctWq.getId());

        partialUpdatedFimSettAcctWq
            .settCcy(UPDATED_SETT_CCY)
            .createdBy(UPDATED_CREATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);

        restFimSettAcctWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimSettAcctWq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimSettAcctWq))
            )
            .andExpect(status().isOk());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeUpdate);
        FimSettAcctWq testFimSettAcctWq = fimSettAcctWqList.get(fimSettAcctWqList.size() - 1);
        assertThat(testFimSettAcctWq.getSettaccId()).isEqualTo(DEFAULT_SETTACC_ID);
        assertThat(testFimSettAcctWq.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testFimSettAcctWq.getSettAcctNbr()).isEqualTo(DEFAULT_SETT_ACCT_NBR);
        assertThat(testFimSettAcctWq.getSettCcy()).isEqualTo(UPDATED_SETT_CCY);
        assertThat(testFimSettAcctWq.getSettAcctStatus()).isEqualTo(DEFAULT_SETT_ACCT_STATUS);
        assertThat(testFimSettAcctWq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimSettAcctWq.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimSettAcctWq.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimSettAcctWq.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimSettAcctWq.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
        assertThat(testFimSettAcctWq.getUploadRemark()).isEqualTo(UPDATED_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void fullUpdateFimSettAcctWqWithPatch() throws Exception {
        // Initialize the database
        fimSettAcctWqRepository.saveAndFlush(fimSettAcctWq);

        int databaseSizeBeforeUpdate = fimSettAcctWqRepository.findAll().size();

        // Update the fimSettAcctWq using partial update
        FimSettAcctWq partialUpdatedFimSettAcctWq = new FimSettAcctWq();
        partialUpdatedFimSettAcctWq.setId(fimSettAcctWq.getId());

        partialUpdatedFimSettAcctWq
            .settaccId(UPDATED_SETTACC_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .settAcctNbr(UPDATED_SETT_ACCT_NBR)
            .settCcy(UPDATED_SETT_CCY)
            .settAcctStatus(UPDATED_SETT_ACCT_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);

        restFimSettAcctWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimSettAcctWq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimSettAcctWq))
            )
            .andExpect(status().isOk());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeUpdate);
        FimSettAcctWq testFimSettAcctWq = fimSettAcctWqList.get(fimSettAcctWqList.size() - 1);
        assertThat(testFimSettAcctWq.getSettaccId()).isEqualTo(UPDATED_SETTACC_ID);
        assertThat(testFimSettAcctWq.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimSettAcctWq.getSettAcctNbr()).isEqualTo(UPDATED_SETT_ACCT_NBR);
        assertThat(testFimSettAcctWq.getSettCcy()).isEqualTo(UPDATED_SETT_CCY);
        assertThat(testFimSettAcctWq.getSettAcctStatus()).isEqualTo(UPDATED_SETT_ACCT_STATUS);
        assertThat(testFimSettAcctWq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimSettAcctWq.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimSettAcctWq.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimSettAcctWq.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimSettAcctWq.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testFimSettAcctWq.getUploadRemark()).isEqualTo(UPDATED_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void patchNonExistingFimSettAcctWq() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctWqRepository.findAll().size();
        fimSettAcctWq.setId(count.incrementAndGet());

        // Create the FimSettAcctWq
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimSettAcctWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fimSettAcctWqDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFimSettAcctWq() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctWqRepository.findAll().size();
        fimSettAcctWq.setId(count.incrementAndGet());

        // Create the FimSettAcctWq
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimSettAcctWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFimSettAcctWq() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctWqRepository.findAll().size();
        fimSettAcctWq.setId(count.incrementAndGet());

        // Create the FimSettAcctWq
        FimSettAcctWqDTO fimSettAcctWqDTO = fimSettAcctWqMapper.toDto(fimSettAcctWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimSettAcctWqMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctWqDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimSettAcctWq in the database
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFimSettAcctWq() throws Exception {
        // Initialize the database
        fimSettAcctWqRepository.saveAndFlush(fimSettAcctWq);

        int databaseSizeBeforeDelete = fimSettAcctWqRepository.findAll().size();

        // Delete the fimSettAcctWq
        restFimSettAcctWqMockMvc
            .perform(delete(ENTITY_API_URL_ID, fimSettAcctWq.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FimSettAcctWq> fimSettAcctWqList = fimSettAcctWqRepository.findAll();
        assertThat(fimSettAcctWqList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
