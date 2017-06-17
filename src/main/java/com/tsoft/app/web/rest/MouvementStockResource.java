package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.MouvementStock;

import com.tsoft.app.repository.MouvementStockRepository;
import com.tsoft.app.service.MouvementStockService;
import com.tsoft.app.web.rest.util.HeaderUtil;
import com.tsoft.app.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing MouvementStock.
 */
@RestController
@RequestMapping("/api")
public class MouvementStockResource {

  private final Logger log = LoggerFactory.getLogger(MouvementStockResource.class);

    private static final String ENTITY_NAME = "mouvementStock";

    private final MouvementStockRepository mouvementStockRepository;

    private final MouvementStockService mouvementStockService;

    public MouvementStockResource(MouvementStockRepository mouvementStockRepository, MouvementStockService mouvementStockService) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockService=mouvementStockService;
    }

    /**
     * POST /mouvement-stocks : Create a new mouvementStock.
     *
     * @param mouvementStock the mouvementStock to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new mouvementStock, or with status 400 (Bad Request) if the
     * mouvementStock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mouvement-stocks")
    @Timed
    public ResponseEntity<MouvementStock> createMouvementStock(@Valid @RequestBody MouvementStock mouvementStock) throws Exception {
        log.debug("REST request to save MouvementStock : {}", mouvementStock);
        if (mouvementStock.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mouvementStock cannot already have an ID")).body(null);
        }
        MouvementStock result = mouvementStockService.save(mouvementStock,true);
        return ResponseEntity.created(new URI("/api/mouvement-stocks/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /mouvement-stocks : Updates an existing mouvementStock.
     *
     * @param mouvementStock the mouvementStock to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * mouvementStock, or with status 400 (Bad Request) if the mouvementStock is
     * not valid, or with status 500 (Internal Server Error) if the
     * mouvementStock couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mouvement-stocks")
    @Timed
    public ResponseEntity<MouvementStock> updateMouvementStock(@Valid @RequestBody MouvementStock mouvementStock) throws Exception {
        log.debug("REST request to update MouvementStock : {}", mouvementStock);
        if (mouvementStock.getId() == null) {
            return createMouvementStock(mouvementStock);
        }
        MouvementStock result = mouvementStockService.save(mouvementStock,true);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mouvementStock.getId().toString()))
                .body(result);
    }

    /**
     * GET /mouvement-stocks : get all the mouvementStocks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * mouvementStocks in body
     */
    @GetMapping("/mouvement-stocks")
    @Timed
    public ResponseEntity<List<MouvementStock>> getAllMouvementStocks(@ApiParam Pageable pageable) {
        log.debug("REST request to get all MouvementStocks");
        Page<MouvementStock> page = mouvementStockRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvement-stocks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mouvement-stocks/:id : get the "id" mouvementStock.
     *
     * @param id the id of the mouvementStock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * mouvementStock, or with status 404 (Not Found)
     */
    @GetMapping("/mouvement-stocks/{id}")
    @Timed
    public ResponseEntity<MouvementStock> getMouvementStock(@PathVariable Long id) {
        log.debug("REST request to get MouvementStock : {}", id);
        MouvementStock mouvementStock = mouvementStockRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mouvementStock));
    }

    /**
     * DELETE /mouvement-stocks/:id : delete the "id" mouvementStock.
     *
     * @param id the id of the mouvementStock to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mouvement-stocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteMouvementStock(@PathVariable Long id) {
        log.debug("REST request to delete MouvementStock : {}", id);
        mouvementStockRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
     /**
     * GET /mouvement-stocks : get a page of MouvementStocks between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of MouvementStock to get
     * @param toDate the end of the time period of MouvementStock to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * MouvementStocks in body
     */
    @GetMapping(path = "/mouvement-stocks", params = {"fromDate", "toDate"})
    public ResponseEntity<List<MouvementStock>> getByDates(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable) {

        Page<MouvementStock> page = mouvementStockRepository.findAllByDateTransactionBetween(fromDate.atTime(0, 0), toDate.atTime(23, 59), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvement-stocks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }



}
