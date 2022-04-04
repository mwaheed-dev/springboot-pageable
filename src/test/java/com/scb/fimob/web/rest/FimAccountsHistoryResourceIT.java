package com.scb.fimob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.scb.fimob.IntegrationTest;
import com.scb.fimob.domain.FimAccountsHistory;
import com.scb.fimob.repository.FimAccountsHistoryRepository;
import com.scb.fimob.service.dto.FimAccountsHistoryDTO;
import com.scb.fimob.service.mapper.FimAccountsHistoryMapper;
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
 * Integration tests for the {@link FimAccountsHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FimAccountsHistoryResourceIT {

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_HISTORY_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HISTORY_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CUST_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUST_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RELN_ID = "AAAAAAAAAA";
    private static final String UPDATED_RELN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RELN_TYPE = "AAAAA";
    private static final String UPDATED_RELN_TYPE = "BBBBB";

    private static final String DEFAULT_OPER_INST = "AAAAAAAAAA";
    private static final String UPDATED_OPER_INST = "BBBBBBBBBB";

    private static final String DEFAULT_IS_ACCT_NBR = "AAAAAAAAAA";
    private static final String UPDATED_IS_ACCT_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_BND_ACCT_NBR = "AAAAAAAAAA";
    private static final String UPDATED_BND_ACCT_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_CLOSING_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLOSING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_SEGMENT = "AAAAAAAAAA";
    private static final String UPDATED_SUB_SEGMENT = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH_CODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ACCT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CTRY_CODE = "AAA";
    private static final String UPDATED_CTRY_CODE = "BBB";

    private static final String DEFAULT_ACCT_OWNERS = "AAAAAAAAAA";
    private static final String UPDATED_ACCT_OWNERS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/fim-accounts-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FimAccountsHistoryRepository fimAccountsHistoryRepository;

    @Autowired
    private FimAccountsHistoryMapper fimAccountsHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFimAccountsHistoryMockMvc;

    private FimAccountsHistory fimAccountsHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimAccountsHistory createEntity(EntityManager em) {
        FimAccountsHistory fimAccountsHistory = new FimAccountsHistory()
            .accountId(DEFAULT_ACCOUNT_ID)
            .historyTs(DEFAULT_HISTORY_TS)
            .custId(DEFAULT_CUST_ID)
            .relnId(DEFAULT_RELN_ID)
            .relnType(DEFAULT_RELN_TYPE)
            .operInst(DEFAULT_OPER_INST)
            .isAcctNbr(DEFAULT_IS_ACCT_NBR)
            .bndAcctNbr(DEFAULT_BND_ACCT_NBR)
            .closingId(DEFAULT_CLOSING_ID)
            .subSegment(DEFAULT_SUB_SEGMENT)
            .branchCode(DEFAULT_BRANCH_CODE)
            .acctStatus(DEFAULT_ACCT_STATUS)
            .ctryCode(DEFAULT_CTRY_CODE)
            .acctOwners(DEFAULT_ACCT_OWNERS)
            .remarks(DEFAULT_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdTs(DEFAULT_CREATED_TS)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTs(DEFAULT_UPDATED_TS)
            .recordStatus(DEFAULT_RECORD_STATUS);
        return fimAccountsHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimAccountsHistory createUpdatedEntity(EntityManager em) {
        FimAccountsHistory fimAccountsHistory = new FimAccountsHistory()
            .accountId(UPDATED_ACCOUNT_ID)
            .historyTs(UPDATED_HISTORY_TS)
            .custId(UPDATED_CUST_ID)
            .relnId(UPDATED_RELN_ID)
            .relnType(UPDATED_RELN_TYPE)
            .operInst(UPDATED_OPER_INST)
            .isAcctNbr(UPDATED_IS_ACCT_NBR)
            .bndAcctNbr(UPDATED_BND_ACCT_NBR)
            .closingId(UPDATED_CLOSING_ID)
            .subSegment(UPDATED_SUB_SEGMENT)
            .branchCode(UPDATED_BRANCH_CODE)
            .acctStatus(UPDATED_ACCT_STATUS)
            .ctryCode(UPDATED_CTRY_CODE)
            .acctOwners(UPDATED_ACCT_OWNERS)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS);
        return fimAccountsHistory;
    }

    @BeforeEach
    public void initTest() {
        fimAccountsHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createFimAccountsHistory() throws Exception {
        int databaseSizeBeforeCreate = fimAccountsHistoryRepository.findAll().size();
        // Create the FimAccountsHistory
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);
        restFimAccountsHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        FimAccountsHistory testFimAccountsHistory = fimAccountsHistoryList.get(fimAccountsHistoryList.size() - 1);
        assertThat(testFimAccountsHistory.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testFimAccountsHistory.getHistoryTs()).isEqualTo(DEFAULT_HISTORY_TS);
        assertThat(testFimAccountsHistory.getCustId()).isEqualTo(DEFAULT_CUST_ID);
        assertThat(testFimAccountsHistory.getRelnId()).isEqualTo(DEFAULT_RELN_ID);
        assertThat(testFimAccountsHistory.getRelnType()).isEqualTo(DEFAULT_RELN_TYPE);
        assertThat(testFimAccountsHistory.getOperInst()).isEqualTo(DEFAULT_OPER_INST);
        assertThat(testFimAccountsHistory.getIsAcctNbr()).isEqualTo(DEFAULT_IS_ACCT_NBR);
        assertThat(testFimAccountsHistory.getBndAcctNbr()).isEqualTo(DEFAULT_BND_ACCT_NBR);
        assertThat(testFimAccountsHistory.getClosingId()).isEqualTo(DEFAULT_CLOSING_ID);
        assertThat(testFimAccountsHistory.getSubSegment()).isEqualTo(DEFAULT_SUB_SEGMENT);
        assertThat(testFimAccountsHistory.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testFimAccountsHistory.getAcctStatus()).isEqualTo(DEFAULT_ACCT_STATUS);
        assertThat(testFimAccountsHistory.getCtryCode()).isEqualTo(DEFAULT_CTRY_CODE);
        assertThat(testFimAccountsHistory.getAcctOwners()).isEqualTo(DEFAULT_ACCT_OWNERS);
        assertThat(testFimAccountsHistory.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testFimAccountsHistory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFimAccountsHistory.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimAccountsHistory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimAccountsHistory.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testFimAccountsHistory.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
    }

    @Test
    @Transactional
    void createFimAccountsHistoryWithExistingId() throws Exception {
        // Create the FimAccountsHistory with an existing ID
        fimAccountsHistory.setId(1L);
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        int databaseSizeBeforeCreate = fimAccountsHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFimAccountsHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsHistoryRepository.findAll().size();
        // set the field null
        fimAccountsHistory.setCustId(null);

        // Create the FimAccountsHistory, which fails.
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        restFimAccountsHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelnIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsHistoryRepository.findAll().size();
        // set the field null
        fimAccountsHistory.setRelnId(null);

        // Create the FimAccountsHistory, which fails.
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        restFimAccountsHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelnTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsHistoryRepository.findAll().size();
        // set the field null
        fimAccountsHistory.setRelnType(null);

        // Create the FimAccountsHistory, which fails.
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        restFimAccountsHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsHistoryRepository.findAll().size();
        // set the field null
        fimAccountsHistory.setIsAcctNbr(null);

        // Create the FimAccountsHistory, which fails.
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        restFimAccountsHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBndAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsHistoryRepository.findAll().size();
        // set the field null
        fimAccountsHistory.setBndAcctNbr(null);

        // Create the FimAccountsHistory, which fails.
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        restFimAccountsHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAcctStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsHistoryRepository.findAll().size();
        // set the field null
        fimAccountsHistory.setAcctStatus(null);

        // Create the FimAccountsHistory, which fails.
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        restFimAccountsHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFimAccountsHistories() throws Exception {
        // Initialize the database
        fimAccountsHistoryRepository.saveAndFlush(fimAccountsHistory);

        // Get all the fimAccountsHistoryList
        restFimAccountsHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fimAccountsHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
            .andExpect(jsonPath("$.[*].historyTs").value(hasItem(DEFAULT_HISTORY_TS.toString())))
            .andExpect(jsonPath("$.[*].custId").value(hasItem(DEFAULT_CUST_ID)))
            .andExpect(jsonPath("$.[*].relnId").value(hasItem(DEFAULT_RELN_ID)))
            .andExpect(jsonPath("$.[*].relnType").value(hasItem(DEFAULT_RELN_TYPE)))
            .andExpect(jsonPath("$.[*].operInst").value(hasItem(DEFAULT_OPER_INST)))
            .andExpect(jsonPath("$.[*].isAcctNbr").value(hasItem(DEFAULT_IS_ACCT_NBR)))
            .andExpect(jsonPath("$.[*].bndAcctNbr").value(hasItem(DEFAULT_BND_ACCT_NBR)))
            .andExpect(jsonPath("$.[*].closingId").value(hasItem(DEFAULT_CLOSING_ID)))
            .andExpect(jsonPath("$.[*].subSegment").value(hasItem(DEFAULT_SUB_SEGMENT)))
            .andExpect(jsonPath("$.[*].branchCode").value(hasItem(DEFAULT_BRANCH_CODE)))
            .andExpect(jsonPath("$.[*].acctStatus").value(hasItem(DEFAULT_ACCT_STATUS)))
            .andExpect(jsonPath("$.[*].ctryCode").value(hasItem(DEFAULT_CTRY_CODE)))
            .andExpect(jsonPath("$.[*].acctOwners").value(hasItem(DEFAULT_ACCT_OWNERS)))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdTs").value(hasItem(DEFAULT_CREATED_TS.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTs").value(hasItem(DEFAULT_UPDATED_TS.toString())))
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS)));
    }

    @Test
    @Transactional
    void getFimAccountsHistory() throws Exception {
        // Initialize the database
        fimAccountsHistoryRepository.saveAndFlush(fimAccountsHistory);

        // Get the fimAccountsHistory
        restFimAccountsHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, fimAccountsHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fimAccountsHistory.getId().intValue()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
            .andExpect(jsonPath("$.historyTs").value(DEFAULT_HISTORY_TS.toString()))
            .andExpect(jsonPath("$.custId").value(DEFAULT_CUST_ID))
            .andExpect(jsonPath("$.relnId").value(DEFAULT_RELN_ID))
            .andExpect(jsonPath("$.relnType").value(DEFAULT_RELN_TYPE))
            .andExpect(jsonPath("$.operInst").value(DEFAULT_OPER_INST))
            .andExpect(jsonPath("$.isAcctNbr").value(DEFAULT_IS_ACCT_NBR))
            .andExpect(jsonPath("$.bndAcctNbr").value(DEFAULT_BND_ACCT_NBR))
            .andExpect(jsonPath("$.closingId").value(DEFAULT_CLOSING_ID))
            .andExpect(jsonPath("$.subSegment").value(DEFAULT_SUB_SEGMENT))
            .andExpect(jsonPath("$.branchCode").value(DEFAULT_BRANCH_CODE))
            .andExpect(jsonPath("$.acctStatus").value(DEFAULT_ACCT_STATUS))
            .andExpect(jsonPath("$.ctryCode").value(DEFAULT_CTRY_CODE))
            .andExpect(jsonPath("$.acctOwners").value(DEFAULT_ACCT_OWNERS))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdTs").value(DEFAULT_CREATED_TS.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTs").value(DEFAULT_UPDATED_TS.toString()))
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS));
    }

    @Test
    @Transactional
    void getNonExistingFimAccountsHistory() throws Exception {
        // Get the fimAccountsHistory
        restFimAccountsHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFimAccountsHistory() throws Exception {
        // Initialize the database
        fimAccountsHistoryRepository.saveAndFlush(fimAccountsHistory);

        int databaseSizeBeforeUpdate = fimAccountsHistoryRepository.findAll().size();

        // Update the fimAccountsHistory
        FimAccountsHistory updatedFimAccountsHistory = fimAccountsHistoryRepository.findById(fimAccountsHistory.getId()).get();
        // Disconnect from session so that the updates on updatedFimAccountsHistory are not directly saved in db
        em.detach(updatedFimAccountsHistory);
        updatedFimAccountsHistory
            .accountId(UPDATED_ACCOUNT_ID)
            .historyTs(UPDATED_HISTORY_TS)
            .custId(UPDATED_CUST_ID)
            .relnId(UPDATED_RELN_ID)
            .relnType(UPDATED_RELN_TYPE)
            .operInst(UPDATED_OPER_INST)
            .isAcctNbr(UPDATED_IS_ACCT_NBR)
            .bndAcctNbr(UPDATED_BND_ACCT_NBR)
            .closingId(UPDATED_CLOSING_ID)
            .subSegment(UPDATED_SUB_SEGMENT)
            .branchCode(UPDATED_BRANCH_CODE)
            .acctStatus(UPDATED_ACCT_STATUS)
            .ctryCode(UPDATED_CTRY_CODE)
            .acctOwners(UPDATED_ACCT_OWNERS)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS);
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(updatedFimAccountsHistory);

        restFimAccountsHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimAccountsHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeUpdate);
        FimAccountsHistory testFimAccountsHistory = fimAccountsHistoryList.get(fimAccountsHistoryList.size() - 1);
        assertThat(testFimAccountsHistory.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimAccountsHistory.getHistoryTs()).isEqualTo(UPDATED_HISTORY_TS);
        assertThat(testFimAccountsHistory.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimAccountsHistory.getRelnId()).isEqualTo(UPDATED_RELN_ID);
        assertThat(testFimAccountsHistory.getRelnType()).isEqualTo(UPDATED_RELN_TYPE);
        assertThat(testFimAccountsHistory.getOperInst()).isEqualTo(UPDATED_OPER_INST);
        assertThat(testFimAccountsHistory.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testFimAccountsHistory.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testFimAccountsHistory.getClosingId()).isEqualTo(UPDATED_CLOSING_ID);
        assertThat(testFimAccountsHistory.getSubSegment()).isEqualTo(UPDATED_SUB_SEGMENT);
        assertThat(testFimAccountsHistory.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testFimAccountsHistory.getAcctStatus()).isEqualTo(UPDATED_ACCT_STATUS);
        assertThat(testFimAccountsHistory.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimAccountsHistory.getAcctOwners()).isEqualTo(UPDATED_ACCT_OWNERS);
        assertThat(testFimAccountsHistory.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testFimAccountsHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimAccountsHistory.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimAccountsHistory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimAccountsHistory.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimAccountsHistory.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingFimAccountsHistory() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsHistoryRepository.findAll().size();
        fimAccountsHistory.setId(count.incrementAndGet());

        // Create the FimAccountsHistory
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimAccountsHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimAccountsHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFimAccountsHistory() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsHistoryRepository.findAll().size();
        fimAccountsHistory.setId(count.incrementAndGet());

        // Create the FimAccountsHistory
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFimAccountsHistory() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsHistoryRepository.findAll().size();
        fimAccountsHistory.setId(count.incrementAndGet());

        // Create the FimAccountsHistory
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFimAccountsHistoryWithPatch() throws Exception {
        // Initialize the database
        fimAccountsHistoryRepository.saveAndFlush(fimAccountsHistory);

        int databaseSizeBeforeUpdate = fimAccountsHistoryRepository.findAll().size();

        // Update the fimAccountsHistory using partial update
        FimAccountsHistory partialUpdatedFimAccountsHistory = new FimAccountsHistory();
        partialUpdatedFimAccountsHistory.setId(fimAccountsHistory.getId());

        partialUpdatedFimAccountsHistory
            .accountId(UPDATED_ACCOUNT_ID)
            .historyTs(UPDATED_HISTORY_TS)
            .custId(UPDATED_CUST_ID)
            .relnId(UPDATED_RELN_ID)
            .relnType(UPDATED_RELN_TYPE)
            .isAcctNbr(UPDATED_IS_ACCT_NBR)
            .bndAcctNbr(UPDATED_BND_ACCT_NBR)
            .closingId(UPDATED_CLOSING_ID)
            .subSegment(UPDATED_SUB_SEGMENT)
            .acctStatus(UPDATED_ACCT_STATUS)
            .ctryCode(UPDATED_CTRY_CODE)
            .acctOwners(UPDATED_ACCT_OWNERS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS);

        restFimAccountsHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimAccountsHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimAccountsHistory))
            )
            .andExpect(status().isOk());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeUpdate);
        FimAccountsHistory testFimAccountsHistory = fimAccountsHistoryList.get(fimAccountsHistoryList.size() - 1);
        assertThat(testFimAccountsHistory.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimAccountsHistory.getHistoryTs()).isEqualTo(UPDATED_HISTORY_TS);
        assertThat(testFimAccountsHistory.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimAccountsHistory.getRelnId()).isEqualTo(UPDATED_RELN_ID);
        assertThat(testFimAccountsHistory.getRelnType()).isEqualTo(UPDATED_RELN_TYPE);
        assertThat(testFimAccountsHistory.getOperInst()).isEqualTo(DEFAULT_OPER_INST);
        assertThat(testFimAccountsHistory.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testFimAccountsHistory.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testFimAccountsHistory.getClosingId()).isEqualTo(UPDATED_CLOSING_ID);
        assertThat(testFimAccountsHistory.getSubSegment()).isEqualTo(UPDATED_SUB_SEGMENT);
        assertThat(testFimAccountsHistory.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testFimAccountsHistory.getAcctStatus()).isEqualTo(UPDATED_ACCT_STATUS);
        assertThat(testFimAccountsHistory.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimAccountsHistory.getAcctOwners()).isEqualTo(UPDATED_ACCT_OWNERS);
        assertThat(testFimAccountsHistory.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testFimAccountsHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimAccountsHistory.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimAccountsHistory.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimAccountsHistory.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testFimAccountsHistory.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateFimAccountsHistoryWithPatch() throws Exception {
        // Initialize the database
        fimAccountsHistoryRepository.saveAndFlush(fimAccountsHistory);

        int databaseSizeBeforeUpdate = fimAccountsHistoryRepository.findAll().size();

        // Update the fimAccountsHistory using partial update
        FimAccountsHistory partialUpdatedFimAccountsHistory = new FimAccountsHistory();
        partialUpdatedFimAccountsHistory.setId(fimAccountsHistory.getId());

        partialUpdatedFimAccountsHistory
            .accountId(UPDATED_ACCOUNT_ID)
            .historyTs(UPDATED_HISTORY_TS)
            .custId(UPDATED_CUST_ID)
            .relnId(UPDATED_RELN_ID)
            .relnType(UPDATED_RELN_TYPE)
            .operInst(UPDATED_OPER_INST)
            .isAcctNbr(UPDATED_IS_ACCT_NBR)
            .bndAcctNbr(UPDATED_BND_ACCT_NBR)
            .closingId(UPDATED_CLOSING_ID)
            .subSegment(UPDATED_SUB_SEGMENT)
            .branchCode(UPDATED_BRANCH_CODE)
            .acctStatus(UPDATED_ACCT_STATUS)
            .ctryCode(UPDATED_CTRY_CODE)
            .acctOwners(UPDATED_ACCT_OWNERS)
            .remarks(UPDATED_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .recordStatus(UPDATED_RECORD_STATUS);

        restFimAccountsHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimAccountsHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimAccountsHistory))
            )
            .andExpect(status().isOk());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeUpdate);
        FimAccountsHistory testFimAccountsHistory = fimAccountsHistoryList.get(fimAccountsHistoryList.size() - 1);
        assertThat(testFimAccountsHistory.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimAccountsHistory.getHistoryTs()).isEqualTo(UPDATED_HISTORY_TS);
        assertThat(testFimAccountsHistory.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimAccountsHistory.getRelnId()).isEqualTo(UPDATED_RELN_ID);
        assertThat(testFimAccountsHistory.getRelnType()).isEqualTo(UPDATED_RELN_TYPE);
        assertThat(testFimAccountsHistory.getOperInst()).isEqualTo(UPDATED_OPER_INST);
        assertThat(testFimAccountsHistory.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testFimAccountsHistory.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testFimAccountsHistory.getClosingId()).isEqualTo(UPDATED_CLOSING_ID);
        assertThat(testFimAccountsHistory.getSubSegment()).isEqualTo(UPDATED_SUB_SEGMENT);
        assertThat(testFimAccountsHistory.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testFimAccountsHistory.getAcctStatus()).isEqualTo(UPDATED_ACCT_STATUS);
        assertThat(testFimAccountsHistory.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimAccountsHistory.getAcctOwners()).isEqualTo(UPDATED_ACCT_OWNERS);
        assertThat(testFimAccountsHistory.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testFimAccountsHistory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimAccountsHistory.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimAccountsHistory.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimAccountsHistory.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimAccountsHistory.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingFimAccountsHistory() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsHistoryRepository.findAll().size();
        fimAccountsHistory.setId(count.incrementAndGet());

        // Create the FimAccountsHistory
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimAccountsHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fimAccountsHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFimAccountsHistory() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsHistoryRepository.findAll().size();
        fimAccountsHistory.setId(count.incrementAndGet());

        // Create the FimAccountsHistory
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFimAccountsHistory() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsHistoryRepository.findAll().size();
        fimAccountsHistory.setId(count.incrementAndGet());

        // Create the FimAccountsHistory
        FimAccountsHistoryDTO fimAccountsHistoryDTO = fimAccountsHistoryMapper.toDto(fimAccountsHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimAccountsHistory in the database
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFimAccountsHistory() throws Exception {
        // Initialize the database
        fimAccountsHistoryRepository.saveAndFlush(fimAccountsHistory);

        int databaseSizeBeforeDelete = fimAccountsHistoryRepository.findAll().size();

        // Delete the fimAccountsHistory
        restFimAccountsHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, fimAccountsHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FimAccountsHistory> fimAccountsHistoryList = fimAccountsHistoryRepository.findAll();
        assertThat(fimAccountsHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
