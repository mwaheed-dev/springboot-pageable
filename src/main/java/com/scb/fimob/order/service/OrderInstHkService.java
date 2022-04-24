package com.scb.fimob.order.service;

import com.scb.fimob.order.domain.OrderInstHk;
import com.scb.fimob.order.repository.OrderInstHkRepository;
import com.scb.fimob.order.service.dto.OrderInstHkDTO;
import com.scb.fimob.order.service.mapper.OrderInstHkMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderInstHk}.
 */
@Service
@Transactional
public class OrderInstHkService {

    private final Logger log = LoggerFactory.getLogger(OrderInstHkService.class);

    private final OrderInstHkRepository orderInstHkRepository;

    private final OrderInstHkMapper orderInstHkMapper;

    public OrderInstHkService(OrderInstHkRepository orderInstHkRepository, OrderInstHkMapper orderInstHkMapper) {
        this.orderInstHkRepository = orderInstHkRepository;
        this.orderInstHkMapper = orderInstHkMapper;
    }

    /**
     * Save a orderInstHk.
     *
     * @param orderInstHkDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderInstHkDTO save(OrderInstHkDTO orderInstHkDTO) {
        log.debug("Request to save OrderInstHk : {}", orderInstHkDTO);
        OrderInstHk orderInstHk = orderInstHkMapper.toEntity(orderInstHkDTO);
        orderInstHk = orderInstHkRepository.save(orderInstHk);
        return orderInstHkMapper.toDto(orderInstHk);
    }

    /**
     * Update a orderInstHk.
     *
     * @param orderInstHkDTO the entity to save.
     * @return the persisted entity.
     */
    public OrderInstHkDTO update(OrderInstHkDTO orderInstHkDTO) {
        log.debug("Request to save OrderInstHk : {}", orderInstHkDTO);
        OrderInstHk orderInstHk = orderInstHkMapper.toEntity(orderInstHkDTO);
        orderInstHk = orderInstHkRepository.save(orderInstHk);
        return orderInstHkMapper.toDto(orderInstHk);
    }

    /**
     * Partially update a orderInstHk.
     *
     * @param orderInstHkDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<OrderInstHkDTO> partialUpdate(OrderInstHkDTO orderInstHkDTO) {
        log.debug("Request to partially update OrderInstHk : {}", orderInstHkDTO);

        return orderInstHkRepository
            .findById(orderInstHkDTO.getId())
            .map(existingOrderInstHk -> {
                orderInstHkMapper.partialUpdate(existingOrderInstHk, orderInstHkDTO);

                return existingOrderInstHk;
            })
            .map(orderInstHkRepository::save)
            .map(orderInstHkMapper::toDto);
    }

    /**
     * Get all the orderInstHks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<OrderInstHkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OrderInstHks");
        return orderInstHkRepository.findAll(pageable).map(orderInstHkMapper::toDto);
    }

    /**
     * Get one orderInstHk by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<OrderInstHkDTO> findOne(Long id) {
        log.debug("Request to get OrderInstHk : {}", id);
        return orderInstHkRepository.findById(id).map(orderInstHkMapper::toDto);
    }

    /**
     * Delete the orderInstHk by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete OrderInstHk : {}", id);
        orderInstHkRepository.deleteById(id);
    }
}
