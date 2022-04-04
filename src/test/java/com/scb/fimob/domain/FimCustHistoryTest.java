package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FimCustHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FimCustHistory.class);
        FimCustHistory fimCustHistory1 = new FimCustHistory();
        fimCustHistory1.setId(1L);
        FimCustHistory fimCustHistory2 = new FimCustHistory();
        fimCustHistory2.setId(fimCustHistory1.getId());
        assertThat(fimCustHistory1).isEqualTo(fimCustHistory2);
        fimCustHistory2.setId(2L);
        assertThat(fimCustHistory1).isNotEqualTo(fimCustHistory2);
        fimCustHistory1.setId(null);
        assertThat(fimCustHistory1).isNotEqualTo(fimCustHistory2);
    }
}
