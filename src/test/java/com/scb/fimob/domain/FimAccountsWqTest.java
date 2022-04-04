package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FimAccountsWqTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FimAccountsWq.class);
        FimAccountsWq fimAccountsWq1 = new FimAccountsWq();
        fimAccountsWq1.setId(1L);
        FimAccountsWq fimAccountsWq2 = new FimAccountsWq();
        fimAccountsWq2.setId(fimAccountsWq1.getId());
        assertThat(fimAccountsWq1).isEqualTo(fimAccountsWq2);
        fimAccountsWq2.setId(2L);
        assertThat(fimAccountsWq1).isNotEqualTo(fimAccountsWq2);
        fimAccountsWq1.setId(null);
        assertThat(fimAccountsWq1).isNotEqualTo(fimAccountsWq2);
    }
}
