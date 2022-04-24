package com.scb.fimob.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderInstHkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderInstHk.class);
        OrderInstHk orderInstHk1 = new OrderInstHk();
        orderInstHk1.setId(1L);
        OrderInstHk orderInstHk2 = new OrderInstHk();
        orderInstHk2.setId(orderInstHk1.getId());
        assertThat(orderInstHk1).isEqualTo(orderInstHk2);
        orderInstHk2.setId(2L);
        assertThat(orderInstHk1).isNotEqualTo(orderInstHk2);
        orderInstHk1.setId(null);
        assertThat(orderInstHk1).isNotEqualTo(orderInstHk2);
    }
}
