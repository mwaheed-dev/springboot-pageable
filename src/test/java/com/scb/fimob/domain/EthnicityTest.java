package com.scb.fimob.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EthnicityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ethnicity.class);
        Ethnicity ethnicity1 = new Ethnicity();
        ethnicity1.setId(1L);
        Ethnicity ethnicity2 = new Ethnicity();
        ethnicity2.setId(ethnicity1.getId());
        assertThat(ethnicity1).isEqualTo(ethnicity2);
        ethnicity2.setId(2L);
        assertThat(ethnicity1).isNotEqualTo(ethnicity2);
        ethnicity1.setId(null);
        assertThat(ethnicity1).isNotEqualTo(ethnicity2);
    }
}
