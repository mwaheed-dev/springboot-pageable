package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FimSettAcctTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FimSettAcct.class);
        FimSettAcct fimSettAcct1 = new FimSettAcct();
        fimSettAcct1.setId(1L);
        FimSettAcct fimSettAcct2 = new FimSettAcct();
        fimSettAcct2.setId(fimSettAcct1.getId());
        assertThat(fimSettAcct1).isEqualTo(fimSettAcct2);
        fimSettAcct2.setId(2L);
        assertThat(fimSettAcct1).isNotEqualTo(fimSettAcct2);
        fimSettAcct1.setId(null);
        assertThat(fimSettAcct1).isNotEqualTo(fimSettAcct2);
    }
}
