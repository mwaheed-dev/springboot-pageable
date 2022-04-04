package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FimAccountsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FimAccounts.class);
        FimAccounts fimAccounts1 = new FimAccounts();
        fimAccounts1.setId(1L);
        FimAccounts fimAccounts2 = new FimAccounts();
        fimAccounts2.setId(fimAccounts1.getId());
        assertThat(fimAccounts1).isEqualTo(fimAccounts2);
        fimAccounts2.setId(2L);
        assertThat(fimAccounts1).isNotEqualTo(fimAccounts2);
        fimAccounts1.setId(null);
        assertThat(fimAccounts1).isNotEqualTo(fimAccounts2);
    }
}
