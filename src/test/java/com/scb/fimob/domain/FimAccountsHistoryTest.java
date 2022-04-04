package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FimAccountsHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FimAccountsHistory.class);
        FimAccountsHistory fimAccountsHistory1 = new FimAccountsHistory();
        fimAccountsHistory1.setId(1L);
        FimAccountsHistory fimAccountsHistory2 = new FimAccountsHistory();
        fimAccountsHistory2.setId(fimAccountsHistory1.getId());
        assertThat(fimAccountsHistory1).isEqualTo(fimAccountsHistory2);
        fimAccountsHistory2.setId(2L);
        assertThat(fimAccountsHistory1).isNotEqualTo(fimAccountsHistory2);
        fimAccountsHistory1.setId(null);
        assertThat(fimAccountsHistory1).isNotEqualTo(fimAccountsHistory2);
    }
}
