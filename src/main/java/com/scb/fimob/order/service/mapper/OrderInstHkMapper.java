package com.scb.fimob.order.service.mapper;

import com.scb.fimob.order.domain.OrderInstHk;
import com.scb.fimob.order.service.dto.OrderInstHkDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderInstHk} and its DTO {@link OrderInstHkDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderInstHkMapper extends EntityMapper<OrderInstHkDTO, OrderInstHk> {}
