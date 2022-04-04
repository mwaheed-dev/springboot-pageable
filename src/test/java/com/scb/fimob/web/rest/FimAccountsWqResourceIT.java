package com.scb.fimob.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.scb.fimob.IntegrationTest;
import com.scb.fimob.domain.FimAccountsWq;
import com.scb.fimob.repository.FimAccountsWqRepository;
import com.scb.fimob.service.dto.FimAccountsWqDTO;
import com.scb.fimob.service.mapper.FimAccountsWqMapper;
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
 * Integration tests for the {@link FimAccountsWqResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FimAccountsWqResourceIT {

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

    private static final String DEFAULT_UPLOAD_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_REMARK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fim-accounts-wqs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FimAccountsWqRepository fimAccountsWqRepository;

    @Autowired
    private FimAccountsWqMapper fimAccountsWqMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFimAccountsWqMockMvc;

    private FimAccountsWq fimAccountsWq;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimAccountsWq createEntity(EntityManager em) {
        FimAccountsWq fimAccountsWq = new FimAccountsWq()
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
            .recordStatus(DEFAULT_RECORD_STATUS)
            .uploadRemark(DEFAULT_UPLOAD_REMARK);
        return fimAccountsWq;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FimAccountsWq createUpdatedEntity(EntityManager em) {
        FimAccountsWq fimAccountsWq = new FimAccountsWq()
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
            .recordStatus(UPDATED_RECORD_STATUS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);
        return fimAccountsWq;
    }

    @BeforeEach
    public void initTest() {
        fimAccountsWq = createEntity(em);
    }

    @Test
    @Transactional
    void createFimAccountsWq() throws Exception {
        int databaseSizeBeforeCreate = fimAccountsWqRepository.findAll().size();
        // Create the FimAccountsWq
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);
        restFimAccountsWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeCreate + 1);
        FimAccountsWq testFimAccountsWq = fimAccountsWqList.get(fimAccountsWqList.size() - 1);
        assertThat(testFimAccountsWq.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testFimAccountsWq.getCustId()).isEqualTo(DEFAULT_CUST_ID);
        assertThat(testFimAccountsWq.getRelnId()).isEqualTo(DEFAULT_RELN_ID);
        assertThat(testFimAccountsWq.getRelnType()).isEqualTo(DEFAULT_RELN_TYPE);
        assertThat(testFimAccountsWq.getOperInst()).isEqualTo(DEFAULT_OPER_INST);
        assertThat(testFimAccountsWq.getIsAcctNbr()).isEqualTo(DEFAULT_IS_ACCT_NBR);
        assertThat(testFimAccountsWq.getBndAcctNbr()).isEqualTo(DEFAULT_BND_ACCT_NBR);
        assertThat(testFimAccountsWq.getClosingId()).isEqualTo(DEFAULT_CLOSING_ID);
        assertThat(testFimAccountsWq.getSubSegment()).isEqualTo(DEFAULT_SUB_SEGMENT);
        assertThat(testFimAccountsWq.getBranchCode()).isEqualTo(DEFAULT_BRANCH_CODE);
        assertThat(testFimAccountsWq.getAcctStatus()).isEqualTo(DEFAULT_ACCT_STATUS);
        assertThat(testFimAccountsWq.getCtryCode()).isEqualTo(DEFAULT_CTRY_CODE);
        assertThat(testFimAccountsWq.getAcctOwners()).isEqualTo(DEFAULT_ACCT_OWNERS);
        assertThat(testFimAccountsWq.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testFimAccountsWq.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFimAccountsWq.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testFimAccountsWq.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimAccountsWq.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testFimAccountsWq.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
        assertThat(testFimAccountsWq.getUploadRemark()).isEqualTo(DEFAULT_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void createFimAccountsWqWithExistingId() throws Exception {
        // Create the FimAccountsWq with an existing ID
        fimAccountsWq.setId(1L);
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        int databaseSizeBeforeCreate = fimAccountsWqRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFimAccountsWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCustIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsWqRepository.findAll().size();
        // set the field null
        fimAccountsWq.setCustId(null);

        // Create the FimAccountsWq, which fails.
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        restFimAccountsWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelnIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsWqRepository.findAll().size();
        // set the field null
        fimAccountsWq.setRelnId(null);

        // Create the FimAccountsWq, which fails.
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        restFimAccountsWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelnTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsWqRepository.findAll().size();
        // set the field null
        fimAccountsWq.setRelnType(null);

        // Create the FimAccountsWq, which fails.
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        restFimAccountsWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsWqRepository.findAll().size();
        // set the field null
        fimAccountsWq.setIsAcctNbr(null);

        // Create the FimAccountsWq, which fails.
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        restFimAccountsWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBndAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsWqRepository.findAll().size();
        // set the field null
        fimAccountsWq.setBndAcctNbr(null);

        // Create the FimAccountsWq, which fails.
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        restFimAccountsWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAcctStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = fimAccountsWqRepository.findAll().size();
        // set the field null
        fimAccountsWq.setAcctStatus(null);

        // Create the FimAccountsWq, which fails.
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        restFimAccountsWqMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFimAccountsWqs() throws Exception {
        // Initialize the database
        fimAccountsWqRepository.saveAndFlush(fimAccountsWq);

        // Get all the fimAccountsWqList
        restFimAccountsWqMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fimAccountsWq.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].recordStatus").value(hasItem(DEFAULT_RECORD_STATUS)))
            .andExpect(jsonPath("$.[*].uploadRemark").value(hasItem(DEFAULT_UPLOAD_REMARK)));
    }

    @Test
    @Transactional
    void getFimAccountsWq() throws Exception {
        // Initialize the database
        fimAccountsWqRepository.saveAndFlush(fimAccountsWq);

        // Get the fimAccountsWq
        restFimAccountsWqMockMvc
            .perform(get(ENTITY_API_URL_ID, fimAccountsWq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fimAccountsWq.getId().intValue()))
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
            .andExpect(jsonPath("$.recordStatus").value(DEFAULT_RECORD_STATUS))
            .andExpect(jsonPath("$.uploadRemark").value(DEFAULT_UPLOAD_REMARK));
    }

    @Test
    @Transactional
    void getNonExistingFimAccountsWq() throws Exception {
        // Get the fimAccountsWq
        restFimAccountsWqMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFimAccountsWq() throws Exception {
        // Initialize the database
        fimAccountsWqRepository.saveAndFlush(fimAccountsWq);

        int databaseSizeBeforeUpdate = fimAccountsWqRepository.findAll().size();

        // Update the fimAccountsWq
        FimAccountsWq updatedFimAccountsWq = fimAccountsWqRepository.findById(fimAccountsWq.getId()).get();
        // Disconnect from session so that the updates on updatedFimAccountsWq are not directly saved in db
        em.detach(updatedFimAccountsWq);
        updatedFimAccountsWq
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
            .recordStatus(UPDATED_RECORD_STATUS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(updatedFimAccountsWq);

        restFimAccountsWqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimAccountsWqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isOk());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeUpdate);
        FimAccountsWq testFimAccountsWq = fimAccountsWqList.get(fimAccountsWqList.size() - 1);
        assertThat(testFimAccountsWq.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimAccountsWq.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimAccountsWq.getRelnId()).isEqualTo(UPDATED_RELN_ID);
        assertThat(testFimAccountsWq.getRelnType()).isEqualTo(UPDATED_RELN_TYPE);
        assertThat(testFimAccountsWq.getOperInst()).isEqualTo(UPDATED_OPER_INST);
        assertThat(testFimAccountsWq.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testFimAccountsWq.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testFimAccountsWq.getClosingId()).isEqualTo(UPDATED_CLOSING_ID);
        assertThat(testFimAccountsWq.getSubSegment()).isEqualTo(UPDATED_SUB_SEGMENT);
        assertThat(testFimAccountsWq.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testFimAccountsWq.getAcctStatus()).isEqualTo(UPDATED_ACCT_STATUS);
        assertThat(testFimAccountsWq.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimAccountsWq.getAcctOwners()).isEqualTo(UPDATED_ACCT_OWNERS);
        assertThat(testFimAccountsWq.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testFimAccountsWq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimAccountsWq.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimAccountsWq.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimAccountsWq.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimAccountsWq.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testFimAccountsWq.getUploadRemark()).isEqualTo(UPDATED_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void putNonExistingFimAccountsWq() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsWqRepository.findAll().size();
        fimAccountsWq.setId(count.incrementAndGet());

        // Create the FimAccountsWq
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimAccountsWqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fimAccountsWqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFimAccountsWq() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsWqRepository.findAll().size();
        fimAccountsWq.setId(count.incrementAndGet());

        // Create the FimAccountsWq
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsWqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFimAccountsWq() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsWqRepository.findAll().size();
        fimAccountsWq.setId(count.incrementAndGet());

        // Create the FimAccountsWq
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsWqMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFimAccountsWqWithPatch() throws Exception {
        // Initialize the database
        fimAccountsWqRepository.saveAndFlush(fimAccountsWq);

        int databaseSizeBeforeUpdate = fimAccountsWqRepository.findAll().size();

        // Update the fimAccountsWq using partial update
        FimAccountsWq partialUpdatedFimAccountsWq = new FimAccountsWq();
        partialUpdatedFimAccountsWq.setId(fimAccountsWq.getId());

        partialUpdatedFimAccountsWq
            .relnId(UPDATED_RELN_ID)
            .isAcctNbr(UPDATED_IS_ACCT_NBR)
            .closingId(UPDATED_CLOSING_ID)
            .branchCode(UPDATED_BRANCH_CODE)
            .ctryCode(UPDATED_CTRY_CODE)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);

        restFimAccountsWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimAccountsWq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimAccountsWq))
            )
            .andExpect(status().isOk());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeUpdate);
        FimAccountsWq testFimAccountsWq = fimAccountsWqList.get(fimAccountsWqList.size() - 1);
        assertThat(testFimAccountsWq.getAccountId()).isEqualTo(DEFAULT_ACCOUNT_ID);
        assertThat(testFimAccountsWq.getCustId()).isEqualTo(DEFAULT_CUST_ID);
        assertThat(testFimAccountsWq.getRelnId()).isEqualTo(UPDATED_RELN_ID);
        assertThat(testFimAccountsWq.getRelnType()).isEqualTo(DEFAULT_RELN_TYPE);
        assertThat(testFimAccountsWq.getOperInst()).isEqualTo(DEFAULT_OPER_INST);
        assertThat(testFimAccountsWq.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testFimAccountsWq.getBndAcctNbr()).isEqualTo(DEFAULT_BND_ACCT_NBR);
        assertThat(testFimAccountsWq.getClosingId()).isEqualTo(UPDATED_CLOSING_ID);
        assertThat(testFimAccountsWq.getSubSegment()).isEqualTo(DEFAULT_SUB_SEGMENT);
        assertThat(testFimAccountsWq.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testFimAccountsWq.getAcctStatus()).isEqualTo(DEFAULT_ACCT_STATUS);
        assertThat(testFimAccountsWq.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimAccountsWq.getAcctOwners()).isEqualTo(DEFAULT_ACCT_OWNERS);
        assertThat(testFimAccountsWq.getRemarks()).isEqualTo(DEFAULT_REMARKS);
        assertThat(testFimAccountsWq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimAccountsWq.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimAccountsWq.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testFimAccountsWq.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testFimAccountsWq.getRecordStatus()).isEqualTo(DEFAULT_RECORD_STATUS);
        assertThat(testFimAccountsWq.getUploadRemark()).isEqualTo(UPDATED_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void fullUpdateFimAccountsWqWithPatch() throws Exception {
        // Initialize the database
        fimAccountsWqRepository.saveAndFlush(fimAccountsWq);

        int databaseSizeBeforeUpdate = fimAccountsWqRepository.findAll().size();

        // Update the fimAccountsWq using partial update
        FimAccountsWq partialUpdatedFimAccountsWq = new FimAccountsWq();
        partialUpdatedFimAccountsWq.setId(fimAccountsWq.getId());

        partialUpdatedFimAccountsWq
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
            .recordStatus(UPDATED_RECORD_STATUS)
            .uploadRemark(UPDATED_UPLOAD_REMARK);

        restFimAccountsWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFimAccountsWq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFimAccountsWq))
            )
            .andExpect(status().isOk());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeUpdate);
        FimAccountsWq testFimAccountsWq = fimAccountsWqList.get(fimAccountsWqList.size() - 1);
        assertThat(testFimAccountsWq.getAccountId()).isEqualTo(UPDATED_ACCOUNT_ID);
        assertThat(testFimAccountsWq.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testFimAccountsWq.getRelnId()).isEqualTo(UPDATED_RELN_ID);
        assertThat(testFimAccountsWq.getRelnType()).isEqualTo(UPDATED_RELN_TYPE);
        assertThat(testFimAccountsWq.getOperInst()).isEqualTo(UPDATED_OPER_INST);
        assertThat(testFimAccountsWq.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testFimAccountsWq.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testFimAccountsWq.getClosingId()).isEqualTo(UPDATED_CLOSING_ID);
        assertThat(testFimAccountsWq.getSubSegment()).isEqualTo(UPDATED_SUB_SEGMENT);
        assertThat(testFimAccountsWq.getBranchCode()).isEqualTo(UPDATED_BRANCH_CODE);
        assertThat(testFimAccountsWq.getAcctStatus()).isEqualTo(UPDATED_ACCT_STATUS);
        assertThat(testFimAccountsWq.getCtryCode()).isEqualTo(UPDATED_CTRY_CODE);
        assertThat(testFimAccountsWq.getAcctOwners()).isEqualTo(UPDATED_ACCT_OWNERS);
        assertThat(testFimAccountsWq.getRemarks()).isEqualTo(UPDATED_REMARKS);
        assertThat(testFimAccountsWq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFimAccountsWq.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testFimAccountsWq.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testFimAccountsWq.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testFimAccountsWq.getRecordStatus()).isEqualTo(UPDATED_RECORD_STATUS);
        assertThat(testFimAccountsWq.getUploadRemark()).isEqualTo(UPDATED_UPLOAD_REMARK);
    }

    @Test
    @Transactional
    void patchNonExistingFimAccountsWq() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsWqRepository.findAll().size();
        fimAccountsWq.setId(count.incrementAndGet());

        // Create the FimAccountsWq
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFimAccountsWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fimAccountsWqDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFimAccountsWq() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsWqRepository.findAll().size();
        fimAccountsWq.setId(count.incrementAndGet());

        // Create the FimAccountsWq
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsWqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFimAccountsWq() throws Exception {
        int databaseSizeBeforeUpdate = fimAccountsWqRepository.findAll().size();
        fimAccountsWq.setId(count.incrementAndGet());

        // Create the FimAccountsWq
        FimAccountsWqDTO fimAccountsWqDTO = fimAccountsWqMapper.toDto(fimAccountsWq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFimAccountsWqMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fimAccountsWqDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FimAccountsWq in the database
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFimAccountsWq() throws Exception {
        // Initialize the database
        fimAccountsWqRepository.saveAndFlush(fimAccountsWq);

        int databaseSizeBeforeDelete = fimAccountsWqRepository.findAll().size();

        // Delete the fimAccountsWq
        restFimAccountsWqMockMvc
            .perform(delete(ENTITY_API_URL_ID, fimAccountsWq.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FimAccountsWq> fimAccountsWqList = fimAccountsWqRepository.findAll();
        assertThat(fimAccountsWqList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
