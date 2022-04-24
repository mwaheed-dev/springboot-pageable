package com.scb.fimob.order.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.scb.fimob.order.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderInstHkDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderInstHkDTO.class);
        OrderInstHkDTO orderInstHkDTO1 = new OrderInstHkDTO();
        orderInstHkDTO1.setId(1L);
        OrderInstHkDTO orderInstHkDTO2 = new OrderInstHkDTO();
        assertThat(orderInstHkDTO1).isNotEqualTo(orderInstHkDTO2);
        orderInstHkDTO2.setId(orderInstHkDTO1.getId());
        assertThat(orderInstHkDTO1).isEqualTo(orderInstHkDTO2);
        orderInstHkDTO2.setId(2L);
        assertThat(orderInstHkDTO1).isNotEqualTo(orderInstHkDTO2);
        orderInstHkDTO1.setId(null);
        assertThat(orderInstHkDTO1).isNotEqualTo(orderInstHkDTO2);
    }
}
