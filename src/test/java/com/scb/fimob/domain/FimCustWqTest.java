package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FimCustWqTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FimCustWq.class);
        FimCustWq fimCustWq1 = new FimCustWq();
        fimCustWq1.setId(1L);
        FimCustWq fimCustWq2 = new FimCustWq();
        fimCustWq2.setId(fimCustWq1.getId());
        assertThat(fimCustWq1).isEqualTo(fimCustWq2);
        fimCustWq2.setId(2L);
        assertThat(fimCustWq1).isNotEqualTo(fimCustWq2);
        fimCustWq1.setId(null);
        assertThat(fimCustWq1).isNotEqualTo(fimCustWq2);
    }
}
