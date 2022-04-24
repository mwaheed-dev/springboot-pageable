package com.scb.fimob.order.web.rest;

import static com.scb.fimob.order.web.rest.TestUtil.sameInstant;
import static com.scb.fimob.order.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.scb.fimob.order.IntegrationTest;
import com.scb.fimob.order.domain.OrderInstHk;
import com.scb.fimob.order.repository.OrderInstHkRepository;
import com.scb.fimob.order.service.dto.OrderInstHkDTO;
import com.scb.fimob.order.service.mapper.OrderInstHkMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link OrderInstHkResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderInstHkResourceIT {

    private static final String DEFAULT_INST_REF_NBR = "AAAAAAAAAA";
    private static final String UPDATED_INST_REF_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_ORDER_REF_NBR = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_REF_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_SECURITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SECURITY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SECURITY_CCY = "AAA";
    private static final String UPDATED_SECURITY_CCY = "BBB";

    private static final String DEFAULT_SECURITY_TYPE = "A";
    private static final String UPDATED_SECURITY_TYPE = "B";

    private static final String DEFAULT_TXN_TYPE = "A";
    private static final String UPDATED_TXN_TYPE = "B";

    private static final String DEFAULT_CHANNEL = "AAA";
    private static final String UPDATED_CHANNEL = "BBB";

    private static final String DEFAULT_ORDER_EXT_REF_ID_1 = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_EXT_REF_ID_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CUST_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUST_ID = "BBBBBBBBBB";

    private static final String DEFAULT_IS_ACCT_NBR = "AAAAAAAAAA";
    private static final String UPDATED_IS_ACCT_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_BND_ACCT_NBR = "AAAAAAAAAA";
    private static final String UPDATED_BND_ACCT_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_INST_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INST_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_INST_STATUS = "AAA";
    private static final String UPDATED_INST_STATUS = "BBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(11);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(10);

    private static final BigDecimal DEFAULT_NEW_QUANTITY = new BigDecimal(11);
    private static final BigDecimal UPDATED_NEW_QUANTITY = new BigDecimal(10);

    private static final BigDecimal DEFAULT_ORIG_IB_PRICE = new BigDecimal(11);
    private static final BigDecimal UPDATED_ORIG_IB_PRICE = new BigDecimal(10);

    private static final BigDecimal DEFAULT_NEW_IB_PRICE = new BigDecimal(11);
    private static final BigDecimal UPDATED_NEW_IB_PRICE = new BigDecimal(10);

    private static final BigDecimal DEFAULT_ORIG_CLIENT_PRICE = new BigDecimal(11);
    private static final BigDecimal UPDATED_ORIG_CLIENT_PRICE = new BigDecimal(10);

    private static final BigDecimal DEFAULT_NEW_CLIENT_PRICE = new BigDecimal(11);
    private static final BigDecimal UPDATED_NEW_CLIENT_PRICE = new BigDecimal(10);

    private static final BigDecimal DEFAULT_BANK_COMM_AMT = new BigDecimal(7);
    private static final BigDecimal UPDATED_BANK_COMM_AMT = new BigDecimal(6);

    private static final BigDecimal DEFAULT_NEW_BANK_COMM_AMT = new BigDecimal(7);
    private static final BigDecimal UPDATED_NEW_BANK_COMM_AMT = new BigDecimal(6);

    private static final BigDecimal DEFAULT_SPREAD = new BigDecimal(7);
    private static final BigDecimal UPDATED_SPREAD = new BigDecimal(6);

    private static final BigDecimal DEFAULT_NEW_SPREAD = new BigDecimal(7);
    private static final BigDecimal UPDATED_NEW_SPREAD = new BigDecimal(6);

    private static final String DEFAULT_SPREAD_OVERRIDE = "A";
    private static final String UPDATED_SPREAD_OVERRIDE = "B";

    private static final String DEFAULT_NEW_SPREAD_OVERRIDE = "A";
    private static final String UPDATED_NEW_SPREAD_OVERRIDE = "B";

    private static final BigDecimal DEFAULT_ACCRUED_INT = new BigDecimal(11);
    private static final BigDecimal UPDATED_ACCRUED_INT = new BigDecimal(10);

    private static final BigDecimal DEFAULT_NEW_ACCRUED_INT = new BigDecimal(11);
    private static final BigDecimal UPDATED_NEW_ACCRUED_INT = new BigDecimal(10);

    private static final BigDecimal DEFAULT_SETTLEMENT_AMT = new BigDecimal(11);
    private static final BigDecimal UPDATED_SETTLEMENT_AMT = new BigDecimal(10);

    private static final BigDecimal DEFAULT_NEW_SETTLEMENT_AMT = new BigDecimal(11);
    private static final BigDecimal UPDATED_NEW_SETTLEMENT_AMT = new BigDecimal(10);

    private static final String DEFAULT_SETT_ACCT_NBR = "AAAAAAAAAA";
    private static final String UPDATED_SETT_ACCT_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_NEW_SETT_ACCT_NBR = "AAAAAAAAAA";
    private static final String UPDATED_NEW_SETT_ACCT_NBR = "BBBBBBBBBB";

    private static final String DEFAULT_SETT_CCY = "AAA";
    private static final String UPDATED_SETT_CCY = "BBB";

    private static final String DEFAULT_SETT_CCY_NEW = "AAA";
    private static final String UPDATED_SETT_CCY_NEW = "BBB";

    private static final Instant DEFAULT_TRADE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRADE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SETTLEMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SETTLEMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INST_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_INST_REMARKS = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBB";

    private static final Instant DEFAULT_CREATED_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBB";

    private static final Instant DEFAULT_UPDATED_TS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_TS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/order-inst-hks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderInstHkRepository orderInstHkRepository;

    @Autowired
    private OrderInstHkMapper orderInstHkMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderInstHkMockMvc;

    private OrderInstHk orderInstHk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderInstHk createEntity(EntityManager em) {
        OrderInstHk orderInstHk = new OrderInstHk()
            .instRefNbr(DEFAULT_INST_REF_NBR)
            .orderRefNbr(DEFAULT_ORDER_REF_NBR)
            .securityCode(DEFAULT_SECURITY_CODE)
            .securityCcy(DEFAULT_SECURITY_CCY)
            .securityType(DEFAULT_SECURITY_TYPE)
            .txnType(DEFAULT_TXN_TYPE)
            .channel(DEFAULT_CHANNEL)
            .orderExtRefId1(DEFAULT_ORDER_EXT_REF_ID_1)
            .custId(DEFAULT_CUST_ID)
            .isAcctNbr(DEFAULT_IS_ACCT_NBR)
            .bndAcctNbr(DEFAULT_BND_ACCT_NBR)
            .instType(DEFAULT_INST_TYPE)
            .instStatus(DEFAULT_INST_STATUS)
            .quantity(DEFAULT_QUANTITY)
            .newQuantity(DEFAULT_NEW_QUANTITY)
            .origIbPrice(DEFAULT_ORIG_IB_PRICE)
            .newIbPrice(DEFAULT_NEW_IB_PRICE)
            .origClientPrice(DEFAULT_ORIG_CLIENT_PRICE)
            .newClientPrice(DEFAULT_NEW_CLIENT_PRICE)
            .bankCommAmt(DEFAULT_BANK_COMM_AMT)
            .newBankCommAmt(DEFAULT_NEW_BANK_COMM_AMT)
            .spread(DEFAULT_SPREAD)
            .newSpread(DEFAULT_NEW_SPREAD)
            .spreadOverride(DEFAULT_SPREAD_OVERRIDE)
            .newSpreadOverride(DEFAULT_NEW_SPREAD_OVERRIDE)
            .accruedInt(DEFAULT_ACCRUED_INT)
            .newAccruedInt(DEFAULT_NEW_ACCRUED_INT)
            .settlementAmt(DEFAULT_SETTLEMENT_AMT)
            .newSettlementAmt(DEFAULT_NEW_SETTLEMENT_AMT)
            .settAcctNbr(DEFAULT_SETT_ACCT_NBR)
            .newSettAcctNbr(DEFAULT_NEW_SETT_ACCT_NBR)
            .settCcy(DEFAULT_SETT_CCY)
            .settCcyNew(DEFAULT_SETT_CCY_NEW)
            .tradeDate(DEFAULT_TRADE_DATE)
            .settlementDate(DEFAULT_SETTLEMENT_DATE)
            .instRemarks(DEFAULT_INST_REMARKS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdTs(DEFAULT_CREATED_TS)
            .updatedBy(DEFAULT_UPDATED_BY)
            .updatedTs(DEFAULT_UPDATED_TS)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return orderInstHk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderInstHk createUpdatedEntity(EntityManager em) {
        OrderInstHk orderInstHk = new OrderInstHk()
            .instRefNbr(UPDATED_INST_REF_NBR)
            .orderRefNbr(UPDATED_ORDER_REF_NBR)
            .securityCode(UPDATED_SECURITY_CODE)
            .securityCcy(UPDATED_SECURITY_CCY)
            .securityType(UPDATED_SECURITY_TYPE)
            .txnType(UPDATED_TXN_TYPE)
            .channel(UPDATED_CHANNEL)
            .orderExtRefId1(UPDATED_ORDER_EXT_REF_ID_1)
            .custId(UPDATED_CUST_ID)
            .isAcctNbr(UPDATED_IS_ACCT_NBR)
            .bndAcctNbr(UPDATED_BND_ACCT_NBR)
            .instType(UPDATED_INST_TYPE)
            .instStatus(UPDATED_INST_STATUS)
            .quantity(UPDATED_QUANTITY)
            .newQuantity(UPDATED_NEW_QUANTITY)
            .origIbPrice(UPDATED_ORIG_IB_PRICE)
            .newIbPrice(UPDATED_NEW_IB_PRICE)
            .origClientPrice(UPDATED_ORIG_CLIENT_PRICE)
            .newClientPrice(UPDATED_NEW_CLIENT_PRICE)
            .bankCommAmt(UPDATED_BANK_COMM_AMT)
            .newBankCommAmt(UPDATED_NEW_BANK_COMM_AMT)
            .spread(UPDATED_SPREAD)
            .newSpread(UPDATED_NEW_SPREAD)
            .spreadOverride(UPDATED_SPREAD_OVERRIDE)
            .newSpreadOverride(UPDATED_NEW_SPREAD_OVERRIDE)
            .accruedInt(UPDATED_ACCRUED_INT)
            .newAccruedInt(UPDATED_NEW_ACCRUED_INT)
            .settlementAmt(UPDATED_SETTLEMENT_AMT)
            .newSettlementAmt(UPDATED_NEW_SETTLEMENT_AMT)
            .settAcctNbr(UPDATED_SETT_ACCT_NBR)
            .newSettAcctNbr(UPDATED_NEW_SETT_ACCT_NBR)
            .settCcy(UPDATED_SETT_CCY)
            .settCcyNew(UPDATED_SETT_CCY_NEW)
            .tradeDate(UPDATED_TRADE_DATE)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .instRemarks(UPDATED_INST_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .updatedDate(UPDATED_UPDATED_DATE);
        return orderInstHk;
    }

    @BeforeEach
    public void initTest() {
        orderInstHk = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderInstHk() throws Exception {
        int databaseSizeBeforeCreate = orderInstHkRepository.findAll().size();
        // Create the OrderInstHk
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);
        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeCreate + 1);
        OrderInstHk testOrderInstHk = orderInstHkList.get(orderInstHkList.size() - 1);
        assertThat(testOrderInstHk.getInstRefNbr()).isEqualTo(DEFAULT_INST_REF_NBR);
        assertThat(testOrderInstHk.getOrderRefNbr()).isEqualTo(DEFAULT_ORDER_REF_NBR);
        assertThat(testOrderInstHk.getSecurityCode()).isEqualTo(DEFAULT_SECURITY_CODE);
        assertThat(testOrderInstHk.getSecurityCcy()).isEqualTo(DEFAULT_SECURITY_CCY);
        assertThat(testOrderInstHk.getSecurityType()).isEqualTo(DEFAULT_SECURITY_TYPE);
        assertThat(testOrderInstHk.getTxnType()).isEqualTo(DEFAULT_TXN_TYPE);
        assertThat(testOrderInstHk.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testOrderInstHk.getOrderExtRefId1()).isEqualTo(DEFAULT_ORDER_EXT_REF_ID_1);
        assertThat(testOrderInstHk.getCustId()).isEqualTo(DEFAULT_CUST_ID);
        assertThat(testOrderInstHk.getIsAcctNbr()).isEqualTo(DEFAULT_IS_ACCT_NBR);
        assertThat(testOrderInstHk.getBndAcctNbr()).isEqualTo(DEFAULT_BND_ACCT_NBR);
        assertThat(testOrderInstHk.getInstType()).isEqualTo(DEFAULT_INST_TYPE);
        assertThat(testOrderInstHk.getInstStatus()).isEqualTo(DEFAULT_INST_STATUS);
        assertThat(testOrderInstHk.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
        assertThat(testOrderInstHk.getNewQuantity()).isEqualByComparingTo(DEFAULT_NEW_QUANTITY);
        assertThat(testOrderInstHk.getOrigIbPrice()).isEqualByComparingTo(DEFAULT_ORIG_IB_PRICE);
        assertThat(testOrderInstHk.getNewIbPrice()).isEqualByComparingTo(DEFAULT_NEW_IB_PRICE);
        assertThat(testOrderInstHk.getOrigClientPrice()).isEqualByComparingTo(DEFAULT_ORIG_CLIENT_PRICE);
        assertThat(testOrderInstHk.getNewClientPrice()).isEqualByComparingTo(DEFAULT_NEW_CLIENT_PRICE);
        assertThat(testOrderInstHk.getBankCommAmt()).isEqualByComparingTo(DEFAULT_BANK_COMM_AMT);
        assertThat(testOrderInstHk.getNewBankCommAmt()).isEqualByComparingTo(DEFAULT_NEW_BANK_COMM_AMT);
        assertThat(testOrderInstHk.getSpread()).isEqualByComparingTo(DEFAULT_SPREAD);
        assertThat(testOrderInstHk.getNewSpread()).isEqualByComparingTo(DEFAULT_NEW_SPREAD);
        assertThat(testOrderInstHk.getSpreadOverride()).isEqualTo(DEFAULT_SPREAD_OVERRIDE);
        assertThat(testOrderInstHk.getNewSpreadOverride()).isEqualTo(DEFAULT_NEW_SPREAD_OVERRIDE);
        assertThat(testOrderInstHk.getAccruedInt()).isEqualByComparingTo(DEFAULT_ACCRUED_INT);
        assertThat(testOrderInstHk.getNewAccruedInt()).isEqualByComparingTo(DEFAULT_NEW_ACCRUED_INT);
        assertThat(testOrderInstHk.getSettlementAmt()).isEqualByComparingTo(DEFAULT_SETTLEMENT_AMT);
        assertThat(testOrderInstHk.getNewSettlementAmt()).isEqualByComparingTo(DEFAULT_NEW_SETTLEMENT_AMT);
        assertThat(testOrderInstHk.getSettAcctNbr()).isEqualTo(DEFAULT_SETT_ACCT_NBR);
        assertThat(testOrderInstHk.getNewSettAcctNbr()).isEqualTo(DEFAULT_NEW_SETT_ACCT_NBR);
        assertThat(testOrderInstHk.getSettCcy()).isEqualTo(DEFAULT_SETT_CCY);
        assertThat(testOrderInstHk.getSettCcyNew()).isEqualTo(DEFAULT_SETT_CCY_NEW);
        assertThat(testOrderInstHk.getTradeDate()).isEqualTo(DEFAULT_TRADE_DATE);
        assertThat(testOrderInstHk.getSettlementDate()).isEqualTo(DEFAULT_SETTLEMENT_DATE);
        assertThat(testOrderInstHk.getInstRemarks()).isEqualTo(DEFAULT_INST_REMARKS);
        assertThat(testOrderInstHk.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrderInstHk.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testOrderInstHk.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
        assertThat(testOrderInstHk.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testOrderInstHk.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void createOrderInstHkWithExistingId() throws Exception {
        // Create the OrderInstHk with an existing ID
        orderInstHk.setId(1L);
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        int databaseSizeBeforeCreate = orderInstHkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInstRefNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setInstRefNbr(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderRefNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setOrderRefNbr(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSecurityCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setSecurityCode(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSecurityCcyIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setSecurityCcy(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSecurityTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setSecurityType(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTxnTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setTxnType(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBndAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setBndAcctNbr(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInstTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setInstType(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInstStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setInstStatus(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setQuantity(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrigIbPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setOrigIbPrice(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrigClientPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setOrigClientPrice(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettAcctNbrIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setSettAcctNbr(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettCcyIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setSettCcy(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettCcyNewIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setSettCcyNew(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTradeDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setTradeDate(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSettlementDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setSettlementDate(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkInstRemarksIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setInstRemarks(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderInstHkRepository.findAll().size();
        // set the field null
        orderInstHk.setCreatedBy(null);

        // Create the OrderInstHk, which fails.
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        restOrderInstHkMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrderInstHks() throws Exception {
        // Initialize the database
        orderInstHkRepository.saveAndFlush(orderInstHk);

        // Get all the orderInstHkList
        restOrderInstHkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderInstHk.getId().intValue())))
            .andExpect(jsonPath("$.[*].instRefNbr").value(hasItem(DEFAULT_INST_REF_NBR)))
            .andExpect(jsonPath("$.[*].orderRefNbr").value(hasItem(DEFAULT_ORDER_REF_NBR)))
            .andExpect(jsonPath("$.[*].securityCode").value(hasItem(DEFAULT_SECURITY_CODE)))
            .andExpect(jsonPath("$.[*].securityCcy").value(hasItem(DEFAULT_SECURITY_CCY)))
            .andExpect(jsonPath("$.[*].securityType").value(hasItem(DEFAULT_SECURITY_TYPE)))
            .andExpect(jsonPath("$.[*].txnType").value(hasItem(DEFAULT_TXN_TYPE)))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].orderExtRefId1").value(hasItem(DEFAULT_ORDER_EXT_REF_ID_1)))
            .andExpect(jsonPath("$.[*].custId").value(hasItem(DEFAULT_CUST_ID)))
            .andExpect(jsonPath("$.[*].isAcctNbr").value(hasItem(DEFAULT_IS_ACCT_NBR)))
            .andExpect(jsonPath("$.[*].bndAcctNbr").value(hasItem(DEFAULT_BND_ACCT_NBR)))
            .andExpect(jsonPath("$.[*].instType").value(hasItem(DEFAULT_INST_TYPE)))
            .andExpect(jsonPath("$.[*].instStatus").value(hasItem(DEFAULT_INST_STATUS)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))))
            .andExpect(jsonPath("$.[*].newQuantity").value(hasItem(sameNumber(DEFAULT_NEW_QUANTITY))))
            .andExpect(jsonPath("$.[*].origIbPrice").value(hasItem(sameNumber(DEFAULT_ORIG_IB_PRICE))))
            .andExpect(jsonPath("$.[*].newIbPrice").value(hasItem(sameNumber(DEFAULT_NEW_IB_PRICE))))
            .andExpect(jsonPath("$.[*].origClientPrice").value(hasItem(sameNumber(DEFAULT_ORIG_CLIENT_PRICE))))
            .andExpect(jsonPath("$.[*].newClientPrice").value(hasItem(sameNumber(DEFAULT_NEW_CLIENT_PRICE))))
            .andExpect(jsonPath("$.[*].bankCommAmt").value(hasItem(sameNumber(DEFAULT_BANK_COMM_AMT))))
            .andExpect(jsonPath("$.[*].newBankCommAmt").value(hasItem(sameNumber(DEFAULT_NEW_BANK_COMM_AMT))))
            .andExpect(jsonPath("$.[*].spread").value(hasItem(sameNumber(DEFAULT_SPREAD))))
            .andExpect(jsonPath("$.[*].newSpread").value(hasItem(sameNumber(DEFAULT_NEW_SPREAD))))
            .andExpect(jsonPath("$.[*].spreadOverride").value(hasItem(DEFAULT_SPREAD_OVERRIDE)))
            .andExpect(jsonPath("$.[*].newSpreadOverride").value(hasItem(DEFAULT_NEW_SPREAD_OVERRIDE)))
            .andExpect(jsonPath("$.[*].accruedInt").value(hasItem(sameNumber(DEFAULT_ACCRUED_INT))))
            .andExpect(jsonPath("$.[*].newAccruedInt").value(hasItem(sameNumber(DEFAULT_NEW_ACCRUED_INT))))
            .andExpect(jsonPath("$.[*].settlementAmt").value(hasItem(sameNumber(DEFAULT_SETTLEMENT_AMT))))
            .andExpect(jsonPath("$.[*].newSettlementAmt").value(hasItem(sameNumber(DEFAULT_NEW_SETTLEMENT_AMT))))
            .andExpect(jsonPath("$.[*].settAcctNbr").value(hasItem(DEFAULT_SETT_ACCT_NBR)))
            .andExpect(jsonPath("$.[*].newSettAcctNbr").value(hasItem(DEFAULT_NEW_SETT_ACCT_NBR)))
            .andExpect(jsonPath("$.[*].settCcy").value(hasItem(DEFAULT_SETT_CCY)))
            .andExpect(jsonPath("$.[*].settCcyNew").value(hasItem(DEFAULT_SETT_CCY_NEW)))
            .andExpect(jsonPath("$.[*].tradeDate").value(hasItem(DEFAULT_TRADE_DATE.toString())))
            .andExpect(jsonPath("$.[*].settlementDate").value(hasItem(DEFAULT_SETTLEMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].instRemarks").value(hasItem(DEFAULT_INST_REMARKS)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdTs").value(hasItem(DEFAULT_CREATED_TS.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)))
            .andExpect(jsonPath("$.[*].updatedTs").value(hasItem(DEFAULT_UPDATED_TS.toString())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }

    @Test
    @Transactional
    void getOrderInstHk() throws Exception {
        // Initialize the database
        orderInstHkRepository.saveAndFlush(orderInstHk);

        // Get the orderInstHk
        restOrderInstHkMockMvc
            .perform(get(ENTITY_API_URL_ID, orderInstHk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderInstHk.getId().intValue()))
            .andExpect(jsonPath("$.instRefNbr").value(DEFAULT_INST_REF_NBR))
            .andExpect(jsonPath("$.orderRefNbr").value(DEFAULT_ORDER_REF_NBR))
            .andExpect(jsonPath("$.securityCode").value(DEFAULT_SECURITY_CODE))
            .andExpect(jsonPath("$.securityCcy").value(DEFAULT_SECURITY_CCY))
            .andExpect(jsonPath("$.securityType").value(DEFAULT_SECURITY_TYPE))
            .andExpect(jsonPath("$.txnType").value(DEFAULT_TXN_TYPE))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.orderExtRefId1").value(DEFAULT_ORDER_EXT_REF_ID_1))
            .andExpect(jsonPath("$.custId").value(DEFAULT_CUST_ID))
            .andExpect(jsonPath("$.isAcctNbr").value(DEFAULT_IS_ACCT_NBR))
            .andExpect(jsonPath("$.bndAcctNbr").value(DEFAULT_BND_ACCT_NBR))
            .andExpect(jsonPath("$.instType").value(DEFAULT_INST_TYPE))
            .andExpect(jsonPath("$.instStatus").value(DEFAULT_INST_STATUS))
            .andExpect(jsonPath("$.quantity").value(sameNumber(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.newQuantity").value(sameNumber(DEFAULT_NEW_QUANTITY)))
            .andExpect(jsonPath("$.origIbPrice").value(sameNumber(DEFAULT_ORIG_IB_PRICE)))
            .andExpect(jsonPath("$.newIbPrice").value(sameNumber(DEFAULT_NEW_IB_PRICE)))
            .andExpect(jsonPath("$.origClientPrice").value(sameNumber(DEFAULT_ORIG_CLIENT_PRICE)))
            .andExpect(jsonPath("$.newClientPrice").value(sameNumber(DEFAULT_NEW_CLIENT_PRICE)))
            .andExpect(jsonPath("$.bankCommAmt").value(sameNumber(DEFAULT_BANK_COMM_AMT)))
            .andExpect(jsonPath("$.newBankCommAmt").value(sameNumber(DEFAULT_NEW_BANK_COMM_AMT)))
            .andExpect(jsonPath("$.spread").value(sameNumber(DEFAULT_SPREAD)))
            .andExpect(jsonPath("$.newSpread").value(sameNumber(DEFAULT_NEW_SPREAD)))
            .andExpect(jsonPath("$.spreadOverride").value(DEFAULT_SPREAD_OVERRIDE))
            .andExpect(jsonPath("$.newSpreadOverride").value(DEFAULT_NEW_SPREAD_OVERRIDE))
            .andExpect(jsonPath("$.accruedInt").value(sameNumber(DEFAULT_ACCRUED_INT)))
            .andExpect(jsonPath("$.newAccruedInt").value(sameNumber(DEFAULT_NEW_ACCRUED_INT)))
            .andExpect(jsonPath("$.settlementAmt").value(sameNumber(DEFAULT_SETTLEMENT_AMT)))
            .andExpect(jsonPath("$.newSettlementAmt").value(sameNumber(DEFAULT_NEW_SETTLEMENT_AMT)))
            .andExpect(jsonPath("$.settAcctNbr").value(DEFAULT_SETT_ACCT_NBR))
            .andExpect(jsonPath("$.newSettAcctNbr").value(DEFAULT_NEW_SETT_ACCT_NBR))
            .andExpect(jsonPath("$.settCcy").value(DEFAULT_SETT_CCY))
            .andExpect(jsonPath("$.settCcyNew").value(DEFAULT_SETT_CCY_NEW))
            .andExpect(jsonPath("$.tradeDate").value(DEFAULT_TRADE_DATE.toString()))
            .andExpect(jsonPath("$.settlementDate").value(DEFAULT_SETTLEMENT_DATE.toString()))
            .andExpect(jsonPath("$.instRemarks").value(DEFAULT_INST_REMARKS))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdTs").value(DEFAULT_CREATED_TS.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY))
            .andExpect(jsonPath("$.updatedTs").value(DEFAULT_UPDATED_TS.toString()))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingOrderInstHk() throws Exception {
        // Get the orderInstHk
        restOrderInstHkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderInstHk() throws Exception {
        // Initialize the database
        orderInstHkRepository.saveAndFlush(orderInstHk);

        int databaseSizeBeforeUpdate = orderInstHkRepository.findAll().size();

        // Update the orderInstHk
        OrderInstHk updatedOrderInstHk = orderInstHkRepository.findById(orderInstHk.getId()).get();
        // Disconnect from session so that the updates on updatedOrderInstHk are not directly saved in db
        em.detach(updatedOrderInstHk);
        updatedOrderInstHk
            .instRefNbr(UPDATED_INST_REF_NBR)
            .orderRefNbr(UPDATED_ORDER_REF_NBR)
            .securityCode(UPDATED_SECURITY_CODE)
            .securityCcy(UPDATED_SECURITY_CCY)
            .securityType(UPDATED_SECURITY_TYPE)
            .txnType(UPDATED_TXN_TYPE)
            .channel(UPDATED_CHANNEL)
            .orderExtRefId1(UPDATED_ORDER_EXT_REF_ID_1)
            .custId(UPDATED_CUST_ID)
            .isAcctNbr(UPDATED_IS_ACCT_NBR)
            .bndAcctNbr(UPDATED_BND_ACCT_NBR)
            .instType(UPDATED_INST_TYPE)
            .instStatus(UPDATED_INST_STATUS)
            .quantity(UPDATED_QUANTITY)
            .newQuantity(UPDATED_NEW_QUANTITY)
            .origIbPrice(UPDATED_ORIG_IB_PRICE)
            .newIbPrice(UPDATED_NEW_IB_PRICE)
            .origClientPrice(UPDATED_ORIG_CLIENT_PRICE)
            .newClientPrice(UPDATED_NEW_CLIENT_PRICE)
            .bankCommAmt(UPDATED_BANK_COMM_AMT)
            .newBankCommAmt(UPDATED_NEW_BANK_COMM_AMT)
            .spread(UPDATED_SPREAD)
            .newSpread(UPDATED_NEW_SPREAD)
            .spreadOverride(UPDATED_SPREAD_OVERRIDE)
            .newSpreadOverride(UPDATED_NEW_SPREAD_OVERRIDE)
            .accruedInt(UPDATED_ACCRUED_INT)
            .newAccruedInt(UPDATED_NEW_ACCRUED_INT)
            .settlementAmt(UPDATED_SETTLEMENT_AMT)
            .newSettlementAmt(UPDATED_NEW_SETTLEMENT_AMT)
            .settAcctNbr(UPDATED_SETT_ACCT_NBR)
            .newSettAcctNbr(UPDATED_NEW_SETT_ACCT_NBR)
            .settCcy(UPDATED_SETT_CCY)
            .settCcyNew(UPDATED_SETT_CCY_NEW)
            .tradeDate(UPDATED_TRADE_DATE)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .instRemarks(UPDATED_INST_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .updatedDate(UPDATED_UPDATED_DATE);
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(updatedOrderInstHk);

        restOrderInstHkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderInstHkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeUpdate);
        OrderInstHk testOrderInstHk = orderInstHkList.get(orderInstHkList.size() - 1);
        assertThat(testOrderInstHk.getInstRefNbr()).isEqualTo(UPDATED_INST_REF_NBR);
        assertThat(testOrderInstHk.getOrderRefNbr()).isEqualTo(UPDATED_ORDER_REF_NBR);
        assertThat(testOrderInstHk.getSecurityCode()).isEqualTo(UPDATED_SECURITY_CODE);
        assertThat(testOrderInstHk.getSecurityCcy()).isEqualTo(UPDATED_SECURITY_CCY);
        assertThat(testOrderInstHk.getSecurityType()).isEqualTo(UPDATED_SECURITY_TYPE);
        assertThat(testOrderInstHk.getTxnType()).isEqualTo(UPDATED_TXN_TYPE);
        assertThat(testOrderInstHk.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testOrderInstHk.getOrderExtRefId1()).isEqualTo(UPDATED_ORDER_EXT_REF_ID_1);
        assertThat(testOrderInstHk.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testOrderInstHk.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testOrderInstHk.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testOrderInstHk.getInstType()).isEqualTo(UPDATED_INST_TYPE);
        assertThat(testOrderInstHk.getInstStatus()).isEqualTo(UPDATED_INST_STATUS);
        assertThat(testOrderInstHk.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testOrderInstHk.getNewQuantity()).isEqualByComparingTo(UPDATED_NEW_QUANTITY);
        assertThat(testOrderInstHk.getOrigIbPrice()).isEqualByComparingTo(UPDATED_ORIG_IB_PRICE);
        assertThat(testOrderInstHk.getNewIbPrice()).isEqualByComparingTo(UPDATED_NEW_IB_PRICE);
        assertThat(testOrderInstHk.getOrigClientPrice()).isEqualByComparingTo(UPDATED_ORIG_CLIENT_PRICE);
        assertThat(testOrderInstHk.getNewClientPrice()).isEqualByComparingTo(UPDATED_NEW_CLIENT_PRICE);
        assertThat(testOrderInstHk.getBankCommAmt()).isEqualByComparingTo(UPDATED_BANK_COMM_AMT);
        assertThat(testOrderInstHk.getNewBankCommAmt()).isEqualByComparingTo(UPDATED_NEW_BANK_COMM_AMT);
        assertThat(testOrderInstHk.getSpread()).isEqualByComparingTo(UPDATED_SPREAD);
        assertThat(testOrderInstHk.getNewSpread()).isEqualByComparingTo(UPDATED_NEW_SPREAD);
        assertThat(testOrderInstHk.getSpreadOverride()).isEqualTo(UPDATED_SPREAD_OVERRIDE);
        assertThat(testOrderInstHk.getNewSpreadOverride()).isEqualTo(UPDATED_NEW_SPREAD_OVERRIDE);
        assertThat(testOrderInstHk.getAccruedInt()).isEqualByComparingTo(UPDATED_ACCRUED_INT);
        assertThat(testOrderInstHk.getNewAccruedInt()).isEqualByComparingTo(UPDATED_NEW_ACCRUED_INT);
        assertThat(testOrderInstHk.getSettlementAmt()).isEqualByComparingTo(UPDATED_SETTLEMENT_AMT);
        assertThat(testOrderInstHk.getNewSettlementAmt()).isEqualByComparingTo(UPDATED_NEW_SETTLEMENT_AMT);
        assertThat(testOrderInstHk.getSettAcctNbr()).isEqualTo(UPDATED_SETT_ACCT_NBR);
        assertThat(testOrderInstHk.getNewSettAcctNbr()).isEqualTo(UPDATED_NEW_SETT_ACCT_NBR);
        assertThat(testOrderInstHk.getSettCcy()).isEqualTo(UPDATED_SETT_CCY);
        assertThat(testOrderInstHk.getSettCcyNew()).isEqualTo(UPDATED_SETT_CCY_NEW);
        assertThat(testOrderInstHk.getTradeDate()).isEqualTo(UPDATED_TRADE_DATE);
        assertThat(testOrderInstHk.getSettlementDate()).isEqualTo(UPDATED_SETTLEMENT_DATE);
        assertThat(testOrderInstHk.getInstRemarks()).isEqualTo(UPDATED_INST_REMARKS);
        assertThat(testOrderInstHk.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrderInstHk.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testOrderInstHk.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrderInstHk.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testOrderInstHk.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOrderInstHk() throws Exception {
        int databaseSizeBeforeUpdate = orderInstHkRepository.findAll().size();
        orderInstHk.setId(count.incrementAndGet());

        // Create the OrderInstHk
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderInstHkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderInstHkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderInstHk() throws Exception {
        int databaseSizeBeforeUpdate = orderInstHkRepository.findAll().size();
        orderInstHk.setId(count.incrementAndGet());

        // Create the OrderInstHk
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderInstHkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderInstHk() throws Exception {
        int databaseSizeBeforeUpdate = orderInstHkRepository.findAll().size();
        orderInstHk.setId(count.incrementAndGet());

        // Create the OrderInstHk
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderInstHkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderInstHkWithPatch() throws Exception {
        // Initialize the database
        orderInstHkRepository.saveAndFlush(orderInstHk);

        int databaseSizeBeforeUpdate = orderInstHkRepository.findAll().size();

        // Update the orderInstHk using partial update
        OrderInstHk partialUpdatedOrderInstHk = new OrderInstHk();
        partialUpdatedOrderInstHk.setId(orderInstHk.getId());

        partialUpdatedOrderInstHk
            .bndAcctNbr(UPDATED_BND_ACCT_NBR)
            .instType(UPDATED_INST_TYPE)
            .instStatus(UPDATED_INST_STATUS)
            .quantity(UPDATED_QUANTITY)
            .newQuantity(UPDATED_NEW_QUANTITY)
            .origIbPrice(UPDATED_ORIG_IB_PRICE)
            .newIbPrice(UPDATED_NEW_IB_PRICE)
            .newClientPrice(UPDATED_NEW_CLIENT_PRICE)
            .bankCommAmt(UPDATED_BANK_COMM_AMT)
            .newBankCommAmt(UPDATED_NEW_BANK_COMM_AMT)
            .spread(UPDATED_SPREAD)
            .spreadOverride(UPDATED_SPREAD_OVERRIDE)
            .newAccruedInt(UPDATED_NEW_ACCRUED_INT)
            .settlementAmt(UPDATED_SETTLEMENT_AMT)
            .settCcyNew(UPDATED_SETT_CCY_NEW)
            .tradeDate(UPDATED_TRADE_DATE)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .instRemarks(UPDATED_INST_REMARKS)
            .updatedBy(UPDATED_UPDATED_BY);

        restOrderInstHkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderInstHk.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderInstHk))
            )
            .andExpect(status().isOk());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeUpdate);
        OrderInstHk testOrderInstHk = orderInstHkList.get(orderInstHkList.size() - 1);
        assertThat(testOrderInstHk.getInstRefNbr()).isEqualTo(DEFAULT_INST_REF_NBR);
        assertThat(testOrderInstHk.getOrderRefNbr()).isEqualTo(DEFAULT_ORDER_REF_NBR);
        assertThat(testOrderInstHk.getSecurityCode()).isEqualTo(DEFAULT_SECURITY_CODE);
        assertThat(testOrderInstHk.getSecurityCcy()).isEqualTo(DEFAULT_SECURITY_CCY);
        assertThat(testOrderInstHk.getSecurityType()).isEqualTo(DEFAULT_SECURITY_TYPE);
        assertThat(testOrderInstHk.getTxnType()).isEqualTo(DEFAULT_TXN_TYPE);
        assertThat(testOrderInstHk.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testOrderInstHk.getOrderExtRefId1()).isEqualTo(DEFAULT_ORDER_EXT_REF_ID_1);
        assertThat(testOrderInstHk.getCustId()).isEqualTo(DEFAULT_CUST_ID);
        assertThat(testOrderInstHk.getIsAcctNbr()).isEqualTo(DEFAULT_IS_ACCT_NBR);
        assertThat(testOrderInstHk.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testOrderInstHk.getInstType()).isEqualTo(UPDATED_INST_TYPE);
        assertThat(testOrderInstHk.getInstStatus()).isEqualTo(UPDATED_INST_STATUS);
        assertThat(testOrderInstHk.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testOrderInstHk.getNewQuantity()).isEqualByComparingTo(UPDATED_NEW_QUANTITY);
        assertThat(testOrderInstHk.getOrigIbPrice()).isEqualByComparingTo(UPDATED_ORIG_IB_PRICE);
        assertThat(testOrderInstHk.getNewIbPrice()).isEqualByComparingTo(UPDATED_NEW_IB_PRICE);
        assertThat(testOrderInstHk.getOrigClientPrice()).isEqualByComparingTo(DEFAULT_ORIG_CLIENT_PRICE);
        assertThat(testOrderInstHk.getNewClientPrice()).isEqualByComparingTo(UPDATED_NEW_CLIENT_PRICE);
        assertThat(testOrderInstHk.getBankCommAmt()).isEqualByComparingTo(UPDATED_BANK_COMM_AMT);
        assertThat(testOrderInstHk.getNewBankCommAmt()).isEqualByComparingTo(UPDATED_NEW_BANK_COMM_AMT);
        assertThat(testOrderInstHk.getSpread()).isEqualByComparingTo(UPDATED_SPREAD);
        assertThat(testOrderInstHk.getNewSpread()).isEqualByComparingTo(DEFAULT_NEW_SPREAD);
        assertThat(testOrderInstHk.getSpreadOverride()).isEqualTo(UPDATED_SPREAD_OVERRIDE);
        assertThat(testOrderInstHk.getNewSpreadOverride()).isEqualTo(DEFAULT_NEW_SPREAD_OVERRIDE);
        assertThat(testOrderInstHk.getAccruedInt()).isEqualByComparingTo(DEFAULT_ACCRUED_INT);
        assertThat(testOrderInstHk.getNewAccruedInt()).isEqualByComparingTo(UPDATED_NEW_ACCRUED_INT);
        assertThat(testOrderInstHk.getSettlementAmt()).isEqualByComparingTo(UPDATED_SETTLEMENT_AMT);
        assertThat(testOrderInstHk.getNewSettlementAmt()).isEqualByComparingTo(DEFAULT_NEW_SETTLEMENT_AMT);
        assertThat(testOrderInstHk.getSettAcctNbr()).isEqualTo(DEFAULT_SETT_ACCT_NBR);
        assertThat(testOrderInstHk.getNewSettAcctNbr()).isEqualTo(DEFAULT_NEW_SETT_ACCT_NBR);
        assertThat(testOrderInstHk.getSettCcy()).isEqualTo(DEFAULT_SETT_CCY);
        assertThat(testOrderInstHk.getSettCcyNew()).isEqualTo(UPDATED_SETT_CCY_NEW);
        assertThat(testOrderInstHk.getTradeDate()).isEqualTo(UPDATED_TRADE_DATE);
        assertThat(testOrderInstHk.getSettlementDate()).isEqualTo(UPDATED_SETTLEMENT_DATE);
        assertThat(testOrderInstHk.getInstRemarks()).isEqualTo(UPDATED_INST_REMARKS);
        assertThat(testOrderInstHk.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrderInstHk.getCreatedTs()).isEqualTo(DEFAULT_CREATED_TS);
        assertThat(testOrderInstHk.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrderInstHk.getUpdatedTs()).isEqualTo(DEFAULT_UPDATED_TS);
        assertThat(testOrderInstHk.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOrderInstHkWithPatch() throws Exception {
        // Initialize the database
        orderInstHkRepository.saveAndFlush(orderInstHk);

        int databaseSizeBeforeUpdate = orderInstHkRepository.findAll().size();

        // Update the orderInstHk using partial update
        OrderInstHk partialUpdatedOrderInstHk = new OrderInstHk();
        partialUpdatedOrderInstHk.setId(orderInstHk.getId());

        partialUpdatedOrderInstHk
            .instRefNbr(UPDATED_INST_REF_NBR)
            .orderRefNbr(UPDATED_ORDER_REF_NBR)
            .securityCode(UPDATED_SECURITY_CODE)
            .securityCcy(UPDATED_SECURITY_CCY)
            .securityType(UPDATED_SECURITY_TYPE)
            .txnType(UPDATED_TXN_TYPE)
            .channel(UPDATED_CHANNEL)
            .orderExtRefId1(UPDATED_ORDER_EXT_REF_ID_1)
            .custId(UPDATED_CUST_ID)
            .isAcctNbr(UPDATED_IS_ACCT_NBR)
            .bndAcctNbr(UPDATED_BND_ACCT_NBR)
            .instType(UPDATED_INST_TYPE)
            .instStatus(UPDATED_INST_STATUS)
            .quantity(UPDATED_QUANTITY)
            .newQuantity(UPDATED_NEW_QUANTITY)
            .origIbPrice(UPDATED_ORIG_IB_PRICE)
            .newIbPrice(UPDATED_NEW_IB_PRICE)
            .origClientPrice(UPDATED_ORIG_CLIENT_PRICE)
            .newClientPrice(UPDATED_NEW_CLIENT_PRICE)
            .bankCommAmt(UPDATED_BANK_COMM_AMT)
            .newBankCommAmt(UPDATED_NEW_BANK_COMM_AMT)
            .spread(UPDATED_SPREAD)
            .newSpread(UPDATED_NEW_SPREAD)
            .spreadOverride(UPDATED_SPREAD_OVERRIDE)
            .newSpreadOverride(UPDATED_NEW_SPREAD_OVERRIDE)
            .accruedInt(UPDATED_ACCRUED_INT)
            .newAccruedInt(UPDATED_NEW_ACCRUED_INT)
            .settlementAmt(UPDATED_SETTLEMENT_AMT)
            .newSettlementAmt(UPDATED_NEW_SETTLEMENT_AMT)
            .settAcctNbr(UPDATED_SETT_ACCT_NBR)
            .newSettAcctNbr(UPDATED_NEW_SETT_ACCT_NBR)
            .settCcy(UPDATED_SETT_CCY)
            .settCcyNew(UPDATED_SETT_CCY_NEW)
            .tradeDate(UPDATED_TRADE_DATE)
            .settlementDate(UPDATED_SETTLEMENT_DATE)
            .instRemarks(UPDATED_INST_REMARKS)
            .createdBy(UPDATED_CREATED_BY)
            .createdTs(UPDATED_CREATED_TS)
            .updatedBy(UPDATED_UPDATED_BY)
            .updatedTs(UPDATED_UPDATED_TS)
            .updatedDate(UPDATED_UPDATED_DATE);

        restOrderInstHkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderInstHk.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderInstHk))
            )
            .andExpect(status().isOk());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeUpdate);
        OrderInstHk testOrderInstHk = orderInstHkList.get(orderInstHkList.size() - 1);
        assertThat(testOrderInstHk.getInstRefNbr()).isEqualTo(UPDATED_INST_REF_NBR);
        assertThat(testOrderInstHk.getOrderRefNbr()).isEqualTo(UPDATED_ORDER_REF_NBR);
        assertThat(testOrderInstHk.getSecurityCode()).isEqualTo(UPDATED_SECURITY_CODE);
        assertThat(testOrderInstHk.getSecurityCcy()).isEqualTo(UPDATED_SECURITY_CCY);
        assertThat(testOrderInstHk.getSecurityType()).isEqualTo(UPDATED_SECURITY_TYPE);
        assertThat(testOrderInstHk.getTxnType()).isEqualTo(UPDATED_TXN_TYPE);
        assertThat(testOrderInstHk.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testOrderInstHk.getOrderExtRefId1()).isEqualTo(UPDATED_ORDER_EXT_REF_ID_1);
        assertThat(testOrderInstHk.getCustId()).isEqualTo(UPDATED_CUST_ID);
        assertThat(testOrderInstHk.getIsAcctNbr()).isEqualTo(UPDATED_IS_ACCT_NBR);
        assertThat(testOrderInstHk.getBndAcctNbr()).isEqualTo(UPDATED_BND_ACCT_NBR);
        assertThat(testOrderInstHk.getInstType()).isEqualTo(UPDATED_INST_TYPE);
        assertThat(testOrderInstHk.getInstStatus()).isEqualTo(UPDATED_INST_STATUS);
        assertThat(testOrderInstHk.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testOrderInstHk.getNewQuantity()).isEqualByComparingTo(UPDATED_NEW_QUANTITY);
        assertThat(testOrderInstHk.getOrigIbPrice()).isEqualByComparingTo(UPDATED_ORIG_IB_PRICE);
        assertThat(testOrderInstHk.getNewIbPrice()).isEqualByComparingTo(UPDATED_NEW_IB_PRICE);
        assertThat(testOrderInstHk.getOrigClientPrice()).isEqualByComparingTo(UPDATED_ORIG_CLIENT_PRICE);
        assertThat(testOrderInstHk.getNewClientPrice()).isEqualByComparingTo(UPDATED_NEW_CLIENT_PRICE);
        assertThat(testOrderInstHk.getBankCommAmt()).isEqualByComparingTo(UPDATED_BANK_COMM_AMT);
        assertThat(testOrderInstHk.getNewBankCommAmt()).isEqualByComparingTo(UPDATED_NEW_BANK_COMM_AMT);
        assertThat(testOrderInstHk.getSpread()).isEqualByComparingTo(UPDATED_SPREAD);
        assertThat(testOrderInstHk.getNewSpread()).isEqualByComparingTo(UPDATED_NEW_SPREAD);
        assertThat(testOrderInstHk.getSpreadOverride()).isEqualTo(UPDATED_SPREAD_OVERRIDE);
        assertThat(testOrderInstHk.getNewSpreadOverride()).isEqualTo(UPDATED_NEW_SPREAD_OVERRIDE);
        assertThat(testOrderInstHk.getAccruedInt()).isEqualByComparingTo(UPDATED_ACCRUED_INT);
        assertThat(testOrderInstHk.getNewAccruedInt()).isEqualByComparingTo(UPDATED_NEW_ACCRUED_INT);
        assertThat(testOrderInstHk.getSettlementAmt()).isEqualByComparingTo(UPDATED_SETTLEMENT_AMT);
        assertThat(testOrderInstHk.getNewSettlementAmt()).isEqualByComparingTo(UPDATED_NEW_SETTLEMENT_AMT);
        assertThat(testOrderInstHk.getSettAcctNbr()).isEqualTo(UPDATED_SETT_ACCT_NBR);
        assertThat(testOrderInstHk.getNewSettAcctNbr()).isEqualTo(UPDATED_NEW_SETT_ACCT_NBR);
        assertThat(testOrderInstHk.getSettCcy()).isEqualTo(UPDATED_SETT_CCY);
        assertThat(testOrderInstHk.getSettCcyNew()).isEqualTo(UPDATED_SETT_CCY_NEW);
        assertThat(testOrderInstHk.getTradeDate()).isEqualTo(UPDATED_TRADE_DATE);
        assertThat(testOrderInstHk.getSettlementDate()).isEqualTo(UPDATED_SETTLEMENT_DATE);
        assertThat(testOrderInstHk.getInstRemarks()).isEqualTo(UPDATED_INST_REMARKS);
        assertThat(testOrderInstHk.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrderInstHk.getCreatedTs()).isEqualTo(UPDATED_CREATED_TS);
        assertThat(testOrderInstHk.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
        assertThat(testOrderInstHk.getUpdatedTs()).isEqualTo(UPDATED_UPDATED_TS);
        assertThat(testOrderInstHk.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOrderInstHk() throws Exception {
        int databaseSizeBeforeUpdate = orderInstHkRepository.findAll().size();
        orderInstHk.setId(count.incrementAndGet());

        // Create the OrderInstHk
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderInstHkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderInstHkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderInstHk() throws Exception {
        int databaseSizeBeforeUpdate = orderInstHkRepository.findAll().size();
        orderInstHk.setId(count.incrementAndGet());

        // Create the OrderInstHk
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderInstHkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderInstHk() throws Exception {
        int databaseSizeBeforeUpdate = orderInstHkRepository.findAll().size();
        orderInstHk.setId(count.incrementAndGet());

        // Create the OrderInstHk
        OrderInstHkDTO orderInstHkDTO = orderInstHkMapper.toDto(orderInstHk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderInstHkMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderInstHkDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderInstHk in the database
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderInstHk() throws Exception {
        // Initialize the database
        orderInstHkRepository.saveAndFlush(orderInstHk);

        int databaseSizeBeforeDelete = orderInstHkRepository.findAll().size();

        // Delete the orderInstHk
        restOrderInstHkMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderInstHk.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderInstHk> orderInstHkList = orderInstHkRepository.findAll();
        assertThat(orderInstHkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
