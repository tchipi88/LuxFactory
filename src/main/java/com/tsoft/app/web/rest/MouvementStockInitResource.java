package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.MouvementStockInit;
import com.tsoft.app.domain.MouvementStockOut;

import com.tsoft.app.repository.MouvementStockInitRepository;
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
 * REST controller for managing MouvementStockInit.
 */
@RestController
@RequestMapping("/api")
public class MouvementStockInitResource {

    private final Logger log = LoggerFactory.getLogger(MouvementStockInitResource.class);

    private static final String ENTITY_NAME = "mouvementStockInit";
        
    private final MouvementStockInitRepository mouvementStockInitRepository;


    public MouvementStockInitResource(MouvementStockInitRepository mouvementStockInitRepository) {
        this.mouvementStockInitRepository = mouvementStockInitRepository;
    }

    /**
     * POST  /mouvement-stock-inits : Create a new mouvementStockInit.
     *
     * @param mouvementStockInit the mouvementStockInit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mouvementStockInit, or with status 400 (Bad Request) if the mouvementStockInit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mouvement-stock-inits")
    @Timed
    public ResponseEntity<MouvementStockInit> createMouvementStockInit(@Valid @RequestBody MouvementStockInit mouvementStockInit) throws Exception {
        log.debug("REST request to save MouvementStockInit : {}", mouvementStockInit);
        if (mouvementStockInit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mouvementStockInit cannot already have an ID")).body(null);
        }
        MouvementStockInit result = mouvementStockInitRepository.save(mouvementStockInit);
        return ResponseEntity.created(new URI("/api/mouvement-stock-inits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mouvement-stock-inits : Updates an existing mouvementStockInit.
     *
     * @param mouvementStockInit the mouvementStockInit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mouvementStockInit,
     * or with status 400 (Bad Request) if the mouvementStockInit is not valid,
     * or with status 500 (Internal Server Error) if the mouvementStockInit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mouvement-stock-inits")
    @Timed
    public ResponseEntity<MouvementStockInit> updateMouvementStockInit(@Valid @RequestBody MouvementStockInit mouvementStockInit) throws Exception {
        log.debug("REST request to update MouvementStockInit : {}", mouvementStockInit);
        if (mouvementStockInit.getId() == null) {
            return createMouvementStockInit(mouvementStockInit);
        }
        MouvementStockInit result = mouvementStockInitRepository.save(mouvementStockInit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mouvementStockInit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mouvement-stock-inits : get all the mouvementStockInits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mouvementStockInits in body
     */
    @GetMapping("/mouvement-stock-inits")
    @Timed
    public ResponseEntity<List<MouvementStockInit>> getAllMouvementStockInits(@ApiParam Pageable pageable) {
        log.debug("REST request to get all MouvementStockInits");
        Page<MouvementStockInit> page = mouvementStockInitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvement-stock-inits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /mouvement-stock-inits/:id : get the "id" mouvementStockInit.
     *
     * @param id the id of the mouvementStockInit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mouvementStockInit, or with status 404 (Not Found)
     */
    @GetMapping("/mouvement-stock-inits/{id}")
    @Timed
    public ResponseEntity<MouvementStockInit> getMouvementStockInit(@PathVariable Long id) {
        log.debug("REST request to get MouvementStockInit : {}", id);
        MouvementStockInit mouvementStockInit = mouvementStockInitRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mouvementStockInit));
    }

    /**
     * DELETE  /mouvement-stock-inits/:id : delete the "id" mouvementStockInit.
     *
     * @param id the id of the mouvementStockInit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mouvement-stock-inits/{id}")
    @Timed
    public ResponseEntity<Void> deleteMouvementStockInit(@PathVariable Long id) {
        log.debug("REST request to delete MouvementStockInit : {}", id);
        mouvementStockInitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    
     /**
     * GET /commandess : get a page of Commandes between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of Commande to get
     * @param toDate the end of the time period of Commande to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * Commandes in body
     */
    @GetMapping(path = "/mouvement-stock-inits", params = {"fromDate", "toDate"})
    @Timed
    public ResponseEntity<List<MouvementStockInit>> searchMouvementStockOut(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable, @ApiParam Long  produit,@ApiParam Long  entrepotId) {
        log.debug("REST request to search for a page of MouvementStockOut for  {}  to {}", fromDate, toDate);
        Page<MouvementStockInit> page = mouvementStockInitRepository.findAllByEntrepotIdAndProduitAndFirstDateBetween(entrepotId,produit,fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvement-stock-inits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
   


}
