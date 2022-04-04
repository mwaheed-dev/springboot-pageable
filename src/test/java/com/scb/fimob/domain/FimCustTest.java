package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FimCustTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FimCust.class);
        FimCust fimCust1 = new FimCust();
        fimCust1.setId(1L);
        FimCust fimCust2 = new FimCust();
        fimCust2.setId(fimCust1.getId());
        assertThat(fimCust1).isEqualTo(fimCust2);
        fimCust2.setId(2L);
        assertThat(fimCust1).isNotEqualTo(fimCust2);
        fimCust1.setId(null);
        assertThat(fimCust1).isNotEqualTo(fimCust2);
    }
}
