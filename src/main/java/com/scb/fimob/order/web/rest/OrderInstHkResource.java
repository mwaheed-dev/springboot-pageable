package com.scb.fimob.order.web.rest;

import com.scb.fimob.order.repository.OrderInstHkRepository;
import com.scb.fimob.order.service.OrderInstHkService;
import com.scb.fimob.order.service.dto.OrderInstHkDTO;
import com.scb.fimob.order.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.scb.fimob.order.domain.OrderInstHk}.
 */
@RestController
@RequestMapping("/api")
public class OrderInstHkResource {

    private final Logger log = LoggerFactory.getLogger(OrderInstHkResource.class);

    private static final String ENTITY_NAME = "fimOrderInstHk";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderInstHkService orderInstHkService;

    private final OrderInstHkRepository orderInstHkRepository;

    public OrderInstHkResource(OrderInstHkService orderInstHkService, OrderInstHkRepository orderInstHkRepository) {
        this.orderInstHkService = orderInstHkService;
        this.orderInstHkRepository = orderInstHkRepository;
    }

    /**
     * {@code POST  /order-inst-hks} : Create a new orderInstHk.
     *
     * @param orderInstHkDTO the orderInstHkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderInstHkDTO, or with status {@code 400 (Bad Request)} if the orderInstHk has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-inst-hks")
    public ResponseEntity<OrderInstHkDTO> createOrderInstHk(@Valid @RequestBody OrderInstHkDTO orderInstHkDTO) throws URISyntaxException {
        log.debug("REST request to save OrderInstHk : {}", orderInstHkDTO);
        if (orderInstHkDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderInstHk cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderInstHkDTO result = orderInstHkService.save(orderInstHkDTO);
        return ResponseEntity
            .created(new URI("/api/order-inst-hks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-inst-hks/:id} : Updates an existing orderInstHk.
     *
     * @param id the id of the orderInstHkDTO to save.
     * @param orderInstHkDTO the orderInstHkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderInstHkDTO,
     * or with status {@code 400 (Bad Request)} if the orderInstHkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderInstHkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-inst-hks/{id}")
    public ResponseEntity<OrderInstHkDTO> updateOrderInstHk(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OrderInstHkDTO orderInstHkDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderInstHk : {}, {}", id, orderInstHkDTO);
        if (orderInstHkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderInstHkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderInstHkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderInstHkDTO result = orderInstHkService.update(orderInstHkDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderInstHkDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-inst-hks/:id} : Partial updates given fields of an existing orderInstHk, field will ignore if it is null
     *
     * @param id the id of the orderInstHkDTO to save.
     * @param orderInstHkDTO the orderInstHkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderInstHkDTO,
     * or with status {@code 400 (Bad Request)} if the orderInstHkDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderInstHkDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderInstHkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-inst-hks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderInstHkDTO> partialUpdateOrderInstHk(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OrderInstHkDTO orderInstHkDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderInstHk partially : {}, {}", id, orderInstHkDTO);
        if (orderInstHkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderInstHkDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderInstHkRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderInstHkDTO> result = orderInstHkService.partialUpdate(orderInstHkDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderInstHkDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-inst-hks} : get all the orderInstHks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderInstHks in body.
     */
    @GetMapping("/order-inst-hks")
    public ResponseEntity<List<OrderInstHkDTO>> getAllOrderInstHks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of OrderInstHks");
        Page<OrderInstHkDTO> page = orderInstHkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-inst-hks/:id} : get the "id" orderInstHk.
     *
     * @param id the id of the orderInstHkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderInstHkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-inst-hks/{id}")
    public ResponseEntity<OrderInstHkDTO> getOrderInstHk(@PathVariable Long id) {
        log.debug("REST request to get OrderInstHk : {}", id);
        Optional<OrderInstHkDTO> orderInstHkDTO = orderInstHkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderInstHkDTO);
    }

    /**
     * {@code DELETE  /order-inst-hks/:id} : delete the "id" orderInstHk.
     *
     * @param id the id of the orderInstHkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-inst-hks/{id}")
    public ResponseEntity<Void> deleteOrderInstHk(@PathVariable Long id) {
        log.debug("REST request to delete OrderInstHk : {}", id);
        orderInstHkService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
