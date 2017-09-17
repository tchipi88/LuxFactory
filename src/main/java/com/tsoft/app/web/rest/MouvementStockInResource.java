package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.MouvementStockIn;
import com.tsoft.app.repository.MouvementStockInRepository;
import com.tsoft.app.web.rest.util.HeaderUtil;
import com.tsoft.app.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing MouvementStockIn.
 */
@RestController
@RequestMapping("/api")
public class MouvementStockInResource {

    private final Logger log = LoggerFactory.getLogger(MouvementStockInResource.class);

    private static final String ENTITY_NAME = "mouvementStockIn";

    private final MouvementStockInRepository mouvementStockInRepository;

    public MouvementStockInResource(MouvementStockInRepository mouvementStockInRepository) {
        this.mouvementStockInRepository = mouvementStockInRepository;
    }

    /**
     * POST /mouvement-stock-ins : Create a new mouvementStockIn.
     *
     * @param mouvementStockIn the mouvementStockIn to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new mouvementStockIn, or with status 400 (Bad Request) if the
     * mouvementStockIn has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mouvement-stock-ins")
    @Timed
    public ResponseEntity<MouvementStockIn> createMouvementStockIn(@Valid @RequestBody MouvementStockIn mouvementStockIn) throws Exception {
        log.debug("REST request to save MouvementStockIn : {}", mouvementStockIn);
        if (mouvementStockIn.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mouvementStockIn cannot already have an ID")).body(null);
        }
        MouvementStockIn result = mouvementStockInRepository.save(mouvementStockIn);
        return ResponseEntity.created(new URI("/api/mouvement-stock-ins/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /mouvement-stock-ins : Updates an existing mouvementStockIn.
     *
     * @param mouvementStockIn the mouvementStockIn to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * mouvementStockIn, or with status 400 (Bad Request) if the
     * mouvementStockIn is not valid, or with status 500 (Internal Server Error)
     * if the mouvementStockIn couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mouvement-stock-ins")
    @Timed
    public ResponseEntity<MouvementStockIn> updateMouvementStockIn(@Valid @RequestBody MouvementStockIn mouvementStockIn) throws Exception {
        log.debug("REST request to update MouvementStockIn : {}", mouvementStockIn);
        if (mouvementStockIn.getId() == null) {
            return createMouvementStockIn(mouvementStockIn);
        }
        MouvementStockIn result = mouvementStockInRepository.save(mouvementStockIn);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mouvementStockIn.getId().toString()))
                .body(result);
    }

    /**
     * GET /mouvement-stock-ins : get all the mouvementStockIns.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * mouvementStockIns in body
     */
    @GetMapping("/mouvement-stock-ins")
    @Timed
    public ResponseEntity<List<MouvementStockIn>> getAllMouvementStockIns(@ApiParam Pageable pageable) {
        log.debug("REST request to get all MouvementStockIns");
        Page<MouvementStockIn> page = mouvementStockInRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvement-stock-ins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /mouvement-stock-ins/:id : get the "id" mouvementStockIn.
     *
     * @param id the id of the mouvementStockIn to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * mouvementStockIn, or with status 404 (Not Found)
     */
    @GetMapping("/mouvement-stock-ins/{id}")
    @Timed
    public ResponseEntity<MouvementStockIn> getMouvementStockIn(@PathVariable Long id) {
        log.debug("REST request to get MouvementStockIn : {}", id);
        MouvementStockIn mouvementStockIn = mouvementStockInRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mouvementStockIn));
    }

    /**
     * DELETE /mouvement-stock-ins/:id : delete the "id" mouvementStockIn.
     *
     * @param id the id of the mouvementStockIn to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mouvement-stock-ins/{id}")
    @Timed
    public ResponseEntity<Void> deleteMouvementStockIn(@PathVariable Long id) {
        log.debug("REST request to delete MouvementStockIn : {}", id);
        mouvementStockInRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping(path = "/mouvement-stock-ins", params = {"fromDate", "toDate"})
    @Timed
    public ResponseEntity<List<MouvementStockIn>> searchMouvementStockIn(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable, @ApiParam Long produit, @ApiParam Long entrepotId) {
        log.debug("REST request to search for a page of MouvementStockOut for  {}  to {}", fromDate, toDate);
        Page<MouvementStockIn> page = mouvementStockInRepository.findAllByEntrepotIdAndProduitAndDateTransactionBetween(entrepotId, produit, fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvement-stock-ins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
