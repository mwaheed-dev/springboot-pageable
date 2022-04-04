package com.scb.fimob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.scb.fimob.IntegrationTest;
import com.scb.fimob.domain.FimSettAcct;
import com.scb.fimob.repository.FimSettAcctRepository;
import com.scb.fimob.service.dto.FimSettAcctDTO;
import com.scb.fimob.service.mapper.FimSettAcctMapper;
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
 * Integration tests for the {@link FimSettAcctResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FimSettAcctResourceIT {

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

    private static final String ENTITY_API_URL = "/api/fim-sett-accts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FimSettAcctRepository fimSettAcctRepository;

    @Autowired
    private FimSettAcctMapper fimSettAcctMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFimSettAcctMockMvc;

    private FimSettAcct fimSettAcct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimSettAcct createEntity(EntityManager em) {
        FimSettAcct fimSettAcct = new FimSettAcct()
            .settaccId(DEFAULT_SETTACC_ID)
            .accountId(DEFAULT_ACCOUNT_ID)
            .settAcctNbr(DEFAULT_SETT_ACCT_NBR)
            .settCcy(DEFAULT_SETT_CCY)
            .settAcctStatus(DEFAULT_SETT_ACCT_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdTs(DEFAULT_CREATED_TS)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTs(DEFAULT_UPDATED_TS)
            .recordStatus(DEFAULT_RECORD_STATUS);
        return fimSettAcct;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimSettAcct createUpdatedEntity(EntityManager em) {
        FimSettAcct fimSettAcct = new FimSettAcct()
            .settaccId(UPDATED_SETTACC_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .settAcctNbr(UPDATED_SETT_ACCT_NBR)
            .settCcy(UPDATED_SETT_CCY)
            .settAcctStatus(UPDATED_SETT_ACCT_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS);
        return fimSettAcct;
    }

    @BeforeEach
    public void initTest() {
        fimSettAcct = createEntity(em);
    }

    @Test
    @Transactional
    void createFimSettAcct() throws Exception {
        int databaseSizeBeforeCreate = fimSettAcctRepository.findAll().size();
        // Create the FimSettAcct
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);
        restFimSettAcctMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeCreate + 1);
        FimSettAcct testFimSettAcct = fimSettAcctList.get(fimSettAcctList.size() - 1);
        assertThat(testFimSettAcct.getSettaccId()).isEqualTo(DEFAULT_SETTACC_ID);
        assertThat(testFimSettAcct.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testFimSettAcct.getSettAcctNbr()).isEqualTo(DEFAULT_SETT_ACCT_NBR);
        assertThat(testFimSettAcct.getSettCcy()).isEqualTo(DEFAULT_SETT_CCY);
        assertThat(testFimSettAcct.getSettAcctStatus()).isEqualTo(DEFAULT_SETT_ACCT_STATUS);
        assertThat(testFimSettAcct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFimSettAcct.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimSettAcct.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimSettAcct.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testFimSettAcct.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
    }

    @Test
    @Transactional
    void createFimSettAcctWithExistingId() throws Exception {
        // Create the FimSettAcct with an existing ID
        fimSettAcct.setId(1L);
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        int databaseSizeBeforeCreate = fimSettAcctRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFimSettAcctMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAccountIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimSettAcctRepository.findAll().size();
        // set the field null
        fimSettAcct.setAccountId(null);

        // Create the FimSettAcct, which fails.
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        restFimSettAcctMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimSettAcctRepository.findAll().size();
        // set the field null
        fimSettAcct.setSettAcctNbr(null);

        // Create the FimSettAcct, which fails.
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        restFimSettAcctMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettCcyIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimSettAcctRepository.findAll().size();
        // set the field null
        fimSettAcct.setSettCcy(null);

        // Create the FimSettAcct, which fails.
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        restFimSettAcctMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettAcctStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimSettAcctRepository.findAll().size();
        // set the field null
        fimSettAcct.setSettAcctStatus(null);

        // Create the FimSettAcct, which fails.
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        restFimSettAcctMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFimSettAccts() throws Exception {
        // Initialize the database
        fimSettAcctRepository.saveAndFlush(fimSettAcct);

        // Get all the fimSettAcctList
        restFimSettAcctMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fimSettAcct.getId().intValue())))
            .andExpect(jsonPath("$.[*].settaccId").value(hasItem(DEFAULT_SETTACC_ID)))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].settAcctNbr").value(hasItem(DEFAULT_SETT_ACCT_NBR)))
            .andExpect(jsonPath("$.[*].settCcy").value(hasItem(DEFAULT_SETT_CCY)))
            .andExpect(jsonPath("$.[*].settAcctStatus").value(hasItem(DEFAULT_SETT_ACCT_STATUS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdTs").value(hasItem(DEFAULT_CREATED_TS.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTs").value(hasItem(DEFAULT_UPDATED_TS.toString())))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS)));
    }

    @Test
    @Transactional
    void getFimSettAcct() throws Exception {
        // Initialize the database
        fimSettAcctRepository.saveAndFlush(fimSettAcct);

        // Get the fimSettAcct
        restFimSettAcctMockMvc
            .perform(get(ENTITY_API_URL_ID, fimSettAcct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fimSettAcct.getId().intValue()))
            .andExpect(jsonPath("$.settaccId").value(DEFAULT_SETTACC_ID))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.settAcctNbr").value(DEFAULT_SETT_ACCT_NBR))
            .andExpect(jsonPath("$.settCcy").value(DEFAULT_SETT_CCY))
            .andExpect(jsonPath("$.settAcctStatus").value(DEFAULT_SETT_ACCT_STATUS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdTs").value(DEFAULT_CREATED_TS.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTs").value(DEFAULT_UPDATED_TS.toString()))
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingFimSettAcct() throws Exception {
        // Get the fimSettAcct
        restFimSettAcctMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFimSettAcct() throws Exception {
        // Initialize the database
        fimSettAcctRepository.saveAndFlush(fimSettAcct);

        int databaseSizeBeforeUpdate = fimSettAcctRepository.findAll().size();

        // Update the fimSettAcct
        FimSettAcct updatedFimSettAcct = fimSettAcctRepository.findById(fimSettAcct.getId()).get();
        // Disconnect from session so that the updates on updatedFimSettAcct are not directly saved in db
        em.detach(updatedFimSettAcct);
        updatedFimSettAcct
            .settaccId(UPDATED_SETTACC_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .settAcctNbr(UPDATED_SETT_ACCT_NBR)
            .settCcy(UPDATED_SETT_CCY)
            .settAcctStatus(UPDATED_SETT_ACCT_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS);
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(updatedFimSettAcct);

        restFimSettAcctMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimSettAcctDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isOk());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeUpdate);
        FimSettAcct testFimSettAcct = fimSettAcctList.get(fimSettAcctList.size() - 1);
        assertThat(testFimSettAcct.getSettaccId()).isEqualTo(UPDATED_SETTACC_ID);
        assertThat(testFimSettAcct.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimSettAcct.getSettAcctNbr()).isEqualTo(UPDATED_SETT_ACCT_NBR);
        assertThat(testFimSettAcct.getSettCcy()).isEqualTo(UPDATED_SETT_CCY);
        assertThat(testFimSettAcct.getSettAcctStatus()).isEqualTo(UPDATED_SETT_ACCT_STATUS);
        assertThat(testFimSettAcct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimSettAcct.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimSettAcct.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimSettAcct.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimSettAcct.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingFimSettAcct() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctRepository.findAll().size();
        fimSettAcct.setId(count.incrementAndGet());

        // Create the FimSettAcct
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimSettAcctMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimSettAcctDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFimSettAcct() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctRepository.findAll().size();
        fimSettAcct.setId(count.incrementAndGet());

        // Create the FimSettAcct
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimSettAcctMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFimSettAcct() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctRepository.findAll().size();
        fimSettAcct.setId(count.incrementAndGet());

        // Create the FimSettAcct
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimSettAcctMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFimSettAcctWithPatch() throws Exception {
        // Initialize the database
        fimSettAcctRepository.saveAndFlush(fimSettAcct);

        int databaseSizeBeforeUpdate = fimSettAcctRepository.findAll().size();

        // Update the fimSettAcct using partial update
        FimSettAcct partialUpdatedFimSettAcct = new FimSettAcct();
        partialUpdatedFimSettAcct.setId(fimSettAcct.getId());

        partialUpdatedFimSettAcct
            .settaccId(UPDATED_SETTACC_ID)
            .settCcy(UPDATED_SETT_CCY)
            .createdBy(UPDATED_CREATED_BY)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS);

        restFimSettAcctMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimSettAcct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimSettAcct))
            )
            .andExpect(status().isOk());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeUpdate);
        FimSettAcct testFimSettAcct = fimSettAcctList.get(fimSettAcctList.size() - 1);
        assertThat(testFimSettAcct.getSettaccId()).isEqualTo(UPDATED_SETTACC_ID);
        assertThat(testFimSettAcct.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testFimSettAcct.getSettAcctNbr()).isEqualTo(DEFAULT_SETT_ACCT_NBR);
        assertThat(testFimSettAcct.getSettCcy()).isEqualTo(UPDATED_SETT_CCY);
        assertThat(testFimSettAcct.getSettAcctStatus()).isEqualTo(DEFAULT_SETT_ACCT_STATUS);
        assertThat(testFimSettAcct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimSettAcct.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimSettAcct.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimSettAcct.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimSettAcct.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateFimSettAcctWithPatch() throws Exception {
        // Initialize the database
        fimSettAcctRepository.saveAndFlush(fimSettAcct);

        int databaseSizeBeforeUpdate = fimSettAcctRepository.findAll().size();

        // Update the fimSettAcct using partial update
        FimSettAcct partialUpdatedFimSettAcct = new FimSettAcct();
        partialUpdatedFimSettAcct.setId(fimSettAcct.getId());

        partialUpdatedFimSettAcct
            .settaccId(UPDATED_SETTACC_ID)
            .accountId(UPDATED_ACCOUNT_ID)
            .settAcctNbr(UPDATED_SETT_ACCT_NBR)
            .settCcy(UPDATED_SETT_CCY)
            .settAcctStatus(UPDATED_SETT_ACCT_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS);

        restFimSettAcctMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimSettAcct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimSettAcct))
            )
            .andExpect(status().isOk());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeUpdate);
        FimSettAcct testFimSettAcct = fimSettAcctList.get(fimSettAcctList.size() - 1);
        assertThat(testFimSettAcct.getSettaccId()).isEqualTo(UPDATED_SETTACC_ID);
        assertThat(testFimSettAcct.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimSettAcct.getSettAcctNbr()).isEqualTo(UPDATED_SETT_ACCT_NBR);
        assertThat(testFimSettAcct.getSettCcy()).isEqualTo(UPDATED_SETT_CCY);
        assertThat(testFimSettAcct.getSettAcctStatus()).isEqualTo(UPDATED_SETT_ACCT_STATUS);
        assertThat(testFimSettAcct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimSettAcct.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimSettAcct.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimSettAcct.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimSettAcct.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingFimSettAcct() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctRepository.findAll().size();
        fimSettAcct.setId(count.incrementAndGet());

        // Create the FimSettAcct
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimSettAcctMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fimSettAcctDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFimSettAcct() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctRepository.findAll().size();
        fimSettAcct.setId(count.incrementAndGet());

        // Create the FimSettAcct
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimSettAcctMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFimSettAcct() throws Exception {
        int databaseSizeBeforeUpdate = fimSettAcctRepository.findAll().size();
        fimSettAcct.setId(count.incrementAndGet());

        // Create the FimSettAcct
        FimSettAcctDTO fimSettAcctDTO = fimSettAcctMapper.toDto(fimSettAcct);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimSettAcctMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fimSettAcctDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimSettAcct in the database
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFimSettAcct() throws Exception {
        // Initialize the database
        fimSettAcctRepository.saveAndFlush(fimSettAcct);

        int databaseSizeBeforeDelete = fimSettAcctRepository.findAll().size();

        // Delete the fimSettAcct
        restFimSettAcctMockMvc
            .perform(delete(ENTITY_API_URL_ID, fimSettAcct.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FimSettAcct> fimSettAcctList = fimSettAcctRepository.findAll();
        assertThat(fimSettAcctList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
