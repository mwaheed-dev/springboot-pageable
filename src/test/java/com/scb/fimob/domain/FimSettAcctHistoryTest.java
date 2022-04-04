package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FimSettAcctHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FimSettAcctHistory.class);
        FimSettAcctHistory fimSettAcctHistory1 = new FimSettAcctHistory();
        fimSettAcctHistory1.setId(1L);
        FimSettAcctHistory fimSettAcctHistory2 = new FimSettAcctHistory();
        fimSettAcctHistory2.setId(fimSettAcctHistory1.getId());
        assertThat(fimSettAcctHistory1).isEqualTo(fimSettAcctHistory2);
        fimSettAcctHistory2.setId(2L);
        assertThat(fimSettAcctHistory1).isNotEqualTo(fimSettAcctHistory2);
        fimSettAcctHistory1.setId(null);
        assertThat(fimSettAcctHistory1).isNotEqualTo(fimSettAcctHistory2);
    }
}
