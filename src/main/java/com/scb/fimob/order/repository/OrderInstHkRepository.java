package com.scb.fimob.order.repository;

import com.scb.fimob.order.domain.OrderInstHk;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OrderInstHk entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderInstHkRepository extends JpaRepository<OrderInstHk, Long> {}
