package com.scb.fimob.web.rest;

import com.scb.fimob.repository.FimAccountsHistoryRepository;
import com.scb.fimob.service.FimAccountsHistoryService;
import com.scb.fimob.service.dto.FimAccountsHistoryDTO;
import com.scb.fimob.web.rest.errors.BadRequestAlertException;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.scb.fimob.domain.FimAccountsHistory}.
 */
@RestController
@RequestMapping("/api")
public class FimAccountsHistoryResource {

    private final Logger log = LoggerFactory.getLogger(FimAccountsHistoryResource.class);

    private static final String ENTITY_NAME = "fimFimAccountsHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FimAccountsHistoryService fimAccountsHistoryService;

    private final FimAccountsHistoryRepository fimAccountsHistoryRepository;

    public FimAccountsHistoryResource(
        FimAccountsHistoryService fimAccountsHistoryService,
        FimAccountsHistoryRepository fimAccountsHistoryRepository
    ) {
        this.fimAccountsHistoryService = fimAccountsHistoryService;
        this.fimAccountsHistoryRepository = fimAccountsHistoryRepository;
    }

    /**
     * {@code POST  /fim-accounts-histories} : Create a new fimAccountsHistory.
     *
     * @param fimAccountsHistoryDTO the fimAccountsHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fimAccountsHistoryDTO, or with status {@code 400 (Bad Request)} if the fimAccountsHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fim-accounts-histories")
    public ResponseEntity<FimAccountsHistoryDTO> createFimAccountsHistory(@Valid @RequestBody FimAccountsHistoryDTO fimAccountsHistoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save FimAccountsHistory : {}", fimAccountsHistoryDTO);
        if (fimAccountsHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new fimAccountsHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FimAccountsHistoryDTO result = fimAccountsHistoryService.save(fimAccountsHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/fim-accounts-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fim-accounts-histories/:id} : Updates an existing fimAccountsHistory.
     *
     * @param id the id of the fimAccountsHistoryDTO to save.
     * @param fimAccountsHistoryDTO the fimAccountsHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fimAccountsHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the fimAccountsHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fimAccountsHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fim-accounts-histories/{id}")
    public ResponseEntity<FimAccountsHistoryDTO> updateFimAccountsHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FimAccountsHistoryDTO fimAccountsHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FimAccountsHistory : {}, {}", id, fimAccountsHistoryDTO);
        if (fimAccountsHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fimAccountsHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fimAccountsHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FimAccountsHistoryDTO result = fimAccountsHistoryService.save(fimAccountsHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fimAccountsHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fim-accounts-histories/:id} : Partial updates given fields of an existing fimAccountsHistory, field will ignore if it is null
     *
     * @param id the id of the fimAccountsHistoryDTO to save.
     * @param fimAccountsHistoryDTO the fimAccountsHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fimAccountsHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the fimAccountsHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fimAccountsHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fimAccountsHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fim-accounts-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FimAccountsHistoryDTO> partialUpdateFimAccountsHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FimAccountsHistoryDTO fimAccountsHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FimAccountsHistory partially : {}, {}", id, fimAccountsHistoryDTO);
        if (fimAccountsHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fimAccountsHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fimAccountsHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FimAccountsHistoryDTO> result = fimAccountsHistoryService.partialUpdate(fimAccountsHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fimAccountsHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fim-accounts-histories} : get all the fimAccountsHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fimAccountsHistories in body.
     */
    @GetMapping("/fim-accounts-histories")
    public List<FimAccountsHistoryDTO> getAllFimAccountsHistories() {
        log.debug("REST request to get all FimAccountsHistories");
        return fimAccountsHistoryService.findAll();
    }

    /**
     * {@code GET  /fim-accounts-histories/:id} : get the "id" fimAccountsHistory.
     *
     * @param id the id of the fimAccountsHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fimAccountsHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fim-accounts-histories/{id}")
    public ResponseEntity<FimAccountsHistoryDTO> getFimAccountsHistory(@PathVariable Long id) {
        log.debug("REST request to get FimAccountsHistory : {}", id);
        Optional<FimAccountsHistoryDTO> fimAccountsHistoryDTO = fimAccountsHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fimAccountsHistoryDTO);
    }

    /**
     * {@code DELETE  /fim-accounts-histories/:id} : delete the "id" fimAccountsHistory.
     *
     * @param id the id of the fimAccountsHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fim-accounts-histories/{id}")
    public ResponseEntity<Void> deleteFimAccountsHistory(@PathVariable Long id) {
        log.debug("REST request to delete FimAccountsHistory : {}", id);
        fimAccountsHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
