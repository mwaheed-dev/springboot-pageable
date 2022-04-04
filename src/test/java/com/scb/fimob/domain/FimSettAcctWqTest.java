package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FimSettAcctWqTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FimSettAcctWq.class);
        FimSettAcctWq fimSettAcctWq1 = new FimSettAcctWq();
        fimSettAcctWq1.setId(1L);
        FimSettAcctWq fimSettAcctWq2 = new FimSettAcctWq();
        fimSettAcctWq2.setId(fimSettAcctWq1.getId());
        assertThat(fimSettAcctWq1).isEqualTo(fimSettAcctWq2);
        fimSettAcctWq2.setId(2L);
        assertThat(fimSettAcctWq1).isNotEqualTo(fimSettAcctWq2);
        fimSettAcctWq1.setId(null);
        assertThat(fimSettAcctWq1).isNotEqualTo(fimSettAcctWq2);
    }
}
