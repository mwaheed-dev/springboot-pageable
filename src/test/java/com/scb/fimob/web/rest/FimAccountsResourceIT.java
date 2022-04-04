package com.scb.fimob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.scb.fimob.IntegrationTest;
import com.scb.fimob.domain.FimAccounts;
import com.scb.fimob.repository.FimAccountsRepository;
import com.scb.fimob.service.dto.FimAccountsDTO;
import com.scb.fimob.service.mapper.FimAccountsMapper;
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
 * Integration tests for the {@link FimAccountsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FimAccountsResourceIT {

    private static final String DEFAULT_ACCOUNT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_ID = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/fim-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FimAccountsRepository fimAccountsRepository;

    @Autowired
    private FimAccountsMapper fimAccountsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFimAccountsMockMvc;

    private FimAccounts fimAccounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimAccounts createEntity(EntityManager em) {
        FimAccounts fimAccounts = new FimAccounts()
            .accountId(DEFAULT_ACCOUNT_ID)
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
        return fimAccounts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimAccounts createUpdatedEntity(EntityManager em) {
        FimAccounts fimAccounts = new FimAccounts()
            .accountId(UPDATED_ACCOUNT_ID)
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
        return fimAccounts;
    }

    @BeforeEach
    public void initTest() {
        fimAccounts = createEntity(em);
    }

    @Test
    @Transactional
    void createFimAccounts() throws Exception {
        int databaseSizeBeforeCreate = fimAccountsRepository.findAll().size();
        // Create the FimAccounts
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);
        restFimAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeCreate + 1);
        FimAccounts testFimAccounts = fimAccountsList.get(fimAccountsList.size() - 1);
        assertThat(testFimAccounts.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testFimAccounts.getCustId()).isEqualTo(DEFAULT_CUST_ID);
        assertThat(testFimAccounts.getRelnId()).isEqualTo(DEFAULT_RELN_ID);
        assertThat(testFimAccounts.getRelnType()).isEqualTo(DEFAULT_RELN_TYPE);
        assertThat(testFimAccounts.getOperInst()).isEqualTo(DEFAULT_OPER_INST);
        assertThat(testFimAccounts.getIsAcctNbr()).isEqualTo(DEFAULT_IS_ACCT_NBR);
        assertThat(testFimAccounts.getBndAcctNbr()).isEqualTo(DEFAULT_BND_ACCT_NBR);
        assertThat(testFimAccounts.getClosingId()).isEqualTo(DEFAULT_CLOSING_ID);
        assertThat(testFimAccounts.getSubSegment()).isEqualTo(DEFAULT_SUB_SEGMENT);
        assertThat(testFimAccounts.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testFimAccounts.getAcctStatus()).isEqualTo(DEFAULT_ACCT_STATUS);
        assertThat(testFimAccounts.getCtryCode()).isEqualTo(DEFAULT_CTRY_CODE);
        assertThat(testFimAccounts.getAcctOwners()).isEqualTo(DEFAULT_ACCT_OWNERS);
        assertThat(testFimAccounts.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testFimAccounts.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFimAccounts.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimAccounts.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimAccounts.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testFimAccounts.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
    }

    @Test
    @Transactional
    void createFimAccountsWithExistingId() throws Exception {
        // Create the FimAccounts with an existing ID
        fimAccounts.setId(1L);
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        int databaseSizeBeforeCreate = fimAccountsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFimAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsRepository.findAll().size();
        // set the field null
        fimAccounts.setCustId(null);

        // Create the FimAccounts, which fails.
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        restFimAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelnIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsRepository.findAll().size();
        // set the field null
        fimAccounts.setRelnId(null);

        // Create the FimAccounts, which fails.
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        restFimAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelnTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsRepository.findAll().size();
        // set the field null
        fimAccounts.setRelnType(null);

        // Create the FimAccounts, which fails.
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        restFimAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsRepository.findAll().size();
        // set the field null
        fimAccounts.setIsAcctNbr(null);

        // Create the FimAccounts, which fails.
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        restFimAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBndAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsRepository.findAll().size();
        // set the field null
        fimAccounts.setBndAcctNbr(null);

        // Create the FimAccounts, which fails.
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        restFimAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAcctStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsRepository.findAll().size();
        // set the field null
        fimAccounts.setAcctStatus(null);

        // Create the FimAccounts, which fails.
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        restFimAccountsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFimAccounts() throws Exception {
        // Initialize the database
        fimAccountsRepository.saveAndFlush(fimAccounts);

        // Get all the fimAccountsList
        restFimAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fimAccounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountId").value(hasItem(DEFAULT_ACCOUNT_ID)))
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
    void getFimAccounts() throws Exception {
        // Initialize the database
        fimAccountsRepository.saveAndFlush(fimAccounts);

        // Get the fimAccounts
        restFimAccountsMockMvc
            .perform(get(ENTITY_API_URL_ID, fimAccounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fimAccounts.getId().intValue()))
            .andExpect(jsonPath("$.accountId").value(DEFAULT_ACCOUNT_ID))
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
    void getNonExistingFimAccounts() throws Exception {
        // Get the fimAccounts
        restFimAccountsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFimAccounts() throws Exception {
        // Initialize the database
        fimAccountsRepository.saveAndFlush(fimAccounts);

        int databaseSizeBeforeUpdate = fimAccountsRepository.findAll().size();

        // Update the fimAccounts
        FimAccounts updatedFimAccounts = fimAccountsRepository.findById(fimAccounts.getId()).get();
        // Disconnect from session so that the updates on updatedFimAccounts are not directly saved in db
        em.detach(updatedFimAccounts);
        updatedFimAccounts
            .accountId(UPDATED_ACCOUNT_ID)
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
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(updatedFimAccounts);

        restFimAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimAccountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isOk());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeUpdate);
        FimAccounts testFimAccounts = fimAccountsList.get(fimAccountsList.size() - 1);
        assertThat(testFimAccounts.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimAccounts.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimAccounts.getRelnId()).isEqualTo(UPDATED_RELN_ID);
        assertThat(testFimAccounts.getRelnType()).isEqualTo(UPDATED_RELN_TYPE);
        assertThat(testFimAccounts.getOperInst()).isEqualTo(UPDATED_OPER_INST);
        assertThat(testFimAccounts.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testFimAccounts.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testFimAccounts.getClosingId()).isEqualTo(UPDATED_CLOSING_ID);
        assertThat(testFimAccounts.getSubSegment()).isEqualTo(UPDATED_SUB_SEGMENT);
        assertThat(testFimAccounts.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testFimAccounts.getAcctStatus()).isEqualTo(UPDATED_ACCT_STATUS);
        assertThat(testFimAccounts.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimAccounts.getAcctOwners()).isEqualTo(UPDATED_ACCT_OWNERS);
        assertThat(testFimAccounts.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testFimAccounts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimAccounts.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimAccounts.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimAccounts.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimAccounts.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingFimAccounts() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsRepository.findAll().size();
        fimAccounts.setId(count.incrementAndGet());

        // Create the FimAccounts
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimAccountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFimAccounts() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsRepository.findAll().size();
        fimAccounts.setId(count.incrementAndGet());

        // Create the FimAccounts
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFimAccounts() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsRepository.findAll().size();
        fimAccounts.setId(count.incrementAndGet());

        // Create the FimAccounts
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFimAccountsWithPatch() throws Exception {
        // Initialize the database
        fimAccountsRepository.saveAndFlush(fimAccounts);

        int databaseSizeBeforeUpdate = fimAccountsRepository.findAll().size();

        // Update the fimAccounts using partial update
        FimAccounts partialUpdatedFimAccounts = new FimAccounts();
        partialUpdatedFimAccounts.setId(fimAccounts.getId());

        partialUpdatedFimAccounts
            .accountId(UPDATED_ACCOUNT_ID)
            .custId(UPDATED_CUST_ID)
            .relnId(UPDATED_RELN_ID)
            .operInst(UPDATED_OPER_INST)
            .isAcctNbr(UPDATED_IS_ACCT_NBR)
            .closingId(UPDATED_CLOSING_ID)
            .subSegment(UPDATED_SUB_SEGMENT)
            .acctStatus(UPDATED_ACCT_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .recordStatus(UPDATED_RECORD_STATUS);

        restFimAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimAccounts))
            )
            .andExpect(status().isOk());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeUpdate);
        FimAccounts testFimAccounts = fimAccountsList.get(fimAccountsList.size() - 1);
        assertThat(testFimAccounts.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimAccounts.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimAccounts.getRelnId()).isEqualTo(UPDATED_RELN_ID);
        assertThat(testFimAccounts.getRelnType()).isEqualTo(DEFAULT_RELN_TYPE);
        assertThat(testFimAccounts.getOperInst()).isEqualTo(UPDATED_OPER_INST);
        assertThat(testFimAccounts.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testFimAccounts.getBndAcctNbr()).isEqualTo(DEFAULT_BND_ACCT_NBR);
        assertThat(testFimAccounts.getClosingId()).isEqualTo(UPDATED_CLOSING_ID);
        assertThat(testFimAccounts.getSubSegment()).isEqualTo(UPDATED_SUB_SEGMENT);
        assertThat(testFimAccounts.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testFimAccounts.getAcctStatus()).isEqualTo(UPDATED_ACCT_STATUS);
        assertThat(testFimAccounts.getCtryCode()).isEqualTo(DEFAULT_CTRY_CODE);
        assertThat(testFimAccounts.getAcctOwners()).isEqualTo(DEFAULT_ACCT_OWNERS);
        assertThat(testFimAccounts.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testFimAccounts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimAccounts.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimAccounts.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimAccounts.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testFimAccounts.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateFimAccountsWithPatch() throws Exception {
        // Initialize the database
        fimAccountsRepository.saveAndFlush(fimAccounts);

        int databaseSizeBeforeUpdate = fimAccountsRepository.findAll().size();

        // Update the fimAccounts using partial update
        FimAccounts partialUpdatedFimAccounts = new FimAccounts();
        partialUpdatedFimAccounts.setId(fimAccounts.getId());

        partialUpdatedFimAccounts
            .accountId(UPDATED_ACCOUNT_ID)
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

        restFimAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimAccounts))
            )
            .andExpect(status().isOk());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeUpdate);
        FimAccounts testFimAccounts = fimAccountsList.get(fimAccountsList.size() - 1);
        assertThat(testFimAccounts.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimAccounts.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimAccounts.getRelnId()).isEqualTo(UPDATED_RELN_ID);
        assertThat(testFimAccounts.getRelnType()).isEqualTo(UPDATED_RELN_TYPE);
        assertThat(testFimAccounts.getOperInst()).isEqualTo(UPDATED_OPER_INST);
        assertThat(testFimAccounts.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testFimAccounts.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testFimAccounts.getClosingId()).isEqualTo(UPDATED_CLOSING_ID);
        assertThat(testFimAccounts.getSubSegment()).isEqualTo(UPDATED_SUB_SEGMENT);
        assertThat(testFimAccounts.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testFimAccounts.getAcctStatus()).isEqualTo(UPDATED_ACCT_STATUS);
        assertThat(testFimAccounts.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimAccounts.getAcctOwners()).isEqualTo(UPDATED_ACCT_OWNERS);
        assertThat(testFimAccounts.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testFimAccounts.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimAccounts.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimAccounts.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimAccounts.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimAccounts.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingFimAccounts() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsRepository.findAll().size();
        fimAccounts.setId(count.incrementAndGet());

        // Create the FimAccounts
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fimAccountsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFimAccounts() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsRepository.findAll().size();
        fimAccounts.setId(count.incrementAndGet());

        // Create the FimAccounts
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFimAccounts() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsRepository.findAll().size();
        fimAccounts.setId(count.incrementAndGet());

        // Create the FimAccounts
        FimAccountsDTO fimAccountsDTO = fimAccountsMapper.toDto(fimAccounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fimAccountsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimAccounts in the database
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFimAccounts() throws Exception {
        // Initialize the database
        fimAccountsRepository.saveAndFlush(fimAccounts);

        int databaseSizeBeforeDelete = fimAccountsRepository.findAll().size();

        // Delete the fimAccounts
        restFimAccountsMockMvc
            .perform(delete(ENTITY_API_URL_ID, fimAccounts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FimAccounts> fimAccountsList = fimAccountsRepository.findAll();
        assertThat(fimAccountsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
