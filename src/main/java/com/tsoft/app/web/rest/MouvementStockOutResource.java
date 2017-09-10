package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.MouvementStockOut;
import com.tsoft.app.domain.enumeration.TypeCommande;

import com.tsoft.app.repository.MouvementStockOutRepository;
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
 * REST controller for managing MouvementStockOut.
 */
@RestController
@RequestMapping("/api")
public class MouvementStockOutResource {

    private final Logger log = LoggerFactory.getLogger(MouvementStockOutResource.class);

    private static final String ENTITY_NAME = "mouvementStockOut";
        
    private final MouvementStockOutRepository mouvementStockOutRepository;


    public MouvementStockOutResource(MouvementStockOutRepository mouvementStockOutRepository) {
        this.mouvementStockOutRepository = mouvementStockOutRepository;
    }

    /**
     * POST  /mouvement-stock-outs : Create a new mouvementStockOut.
     *
     * @param mouvementStockOut the mouvementStockOut to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mouvementStockOut, or with status 400 (Bad Request) if the mouvementStockOut has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mouvement-stock-outs")
    @Timed
    public ResponseEntity<MouvementStockOut> createMouvementStockOut(@Valid @RequestBody MouvementStockOut mouvementStockOut) throws Exception {
        log.debug("REST request to save MouvementStockOut : {}", mouvementStockOut);
        if (mouvementStockOut.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mouvementStockOut cannot already have an ID")).body(null);
        }
        MouvementStockOut result = mouvementStockOutRepository.save(mouvementStockOut);
        return ResponseEntity.created(new URI("/api/mouvement-stock-outs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mouvement-stock-outs : Updates an existing mouvementStockOut.
     *
     * @param mouvementStockOut the mouvementStockOut to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mouvementStockOut,
     * or with status 400 (Bad Request) if the mouvementStockOut is not valid,
     * or with status 500 (Internal Server Error) if the mouvementStockOut couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mouvement-stock-outs")
    @Timed
    public ResponseEntity<MouvementStockOut> updateMouvementStockOut(@Valid @RequestBody MouvementStockOut mouvementStockOut) throws Exception {
        log.debug("REST request to update MouvementStockOut : {}", mouvementStockOut);
        if (mouvementStockOut.getId() == null) {
            return createMouvementStockOut(mouvementStockOut);
        }
        MouvementStockOut result = mouvementStockOutRepository.save(mouvementStockOut);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mouvementStockOut.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mouvement-stock-outs : get all the mouvementStockOuts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of mouvementStockOuts in body
     */
    @GetMapping("/mouvement-stock-outs")
    @Timed
    public ResponseEntity<List<MouvementStockOut>> getAllMouvementStockOuts(@ApiParam Pageable pageable) {
        log.debug("REST request to get all MouvementStockOuts");
        Page<MouvementStockOut> page = mouvementStockOutRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvement-stock-outs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /mouvement-stock-outs/:id : get the "id" mouvementStockOut.
     *
     * @param id the id of the mouvementStockOut to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mouvementStockOut, or with status 404 (Not Found)
     */
    @GetMapping("/mouvement-stock-outs/{id}")
    @Timed
    public ResponseEntity<MouvementStockOut> getMouvementStockOut(@PathVariable Long id) {
        log.debug("REST request to get MouvementStockOut : {}", id);
        MouvementStockOut mouvementStockOut = mouvementStockOutRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mouvementStockOut));
    }

    /**
     * DELETE  /mouvement-stock-outs/:id : delete the "id" mouvementStockOut.
     *
     * @param id the id of the mouvementStockOut to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mouvement-stock-outs/{id}")
    @Timed
    public ResponseEntity<Void> deleteMouvementStockOut(@PathVariable Long id) {
        log.debug("REST request to delete MouvementStockOut : {}", id);
        mouvementStockOutRepository.delete(id);
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
    @GetMapping(path = "/mouvement-stock-outs", params = {"fromDate", "toDate"})
    @Timed
    public ResponseEntity<List<MouvementStockOut>> searchMouvementStockOut(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable, @ApiParam Long  produit,@ApiParam Long  entrepotId) {
        log.debug("REST request to search for a page of MouvementStockOut for  {}  to {}", fromDate, toDate);
        Page<MouvementStockOut> page = mouvementStockOutRepository.findAllByEntrepotIdAndProduitAndDateTransactionBetween(entrepotId,produit,fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvement-stock-outs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }



}
