package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Facture;

import com.tsoft.app.repository.FactureRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Facture.
 */
@RestController
@RequestMapping("/api")
public class FactureResource {

    private final Logger log = LoggerFactory.getLogger(FactureResource.class);

    private static final String ENTITY_NAME = "facture";
        
    private final FactureRepository factureRepository;


    public FactureResource(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    /**
     * POST  /factures : Create a new facture.
     *
     * @param facture the facture to create
     * @return the ResponseEntity with status 201 (Created) and with body the new facture, or with status 400 (Bad Request) if the facture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/factures")
    @Timed
    public ResponseEntity<Facture> createFacture(@Valid @RequestBody Facture facture) throws Exception {
        log.debug("REST request to save Facture : {}", facture);
        if (facture.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new facture cannot already have an ID")).body(null);
        }
        Facture result = factureRepository.save(facture);
        return ResponseEntity.created(new URI("/api/factures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /factures : Updates an existing facture.
     *
     * @param facture the facture to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated facture,
     * or with status 400 (Bad Request) if the facture is not valid,
     * or with status 500 (Internal Server Error) if the facture couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/factures")
    @Timed
    public ResponseEntity<Facture> updateFacture(@Valid @RequestBody Facture facture) throws Exception {
        log.debug("REST request to update Facture : {}", facture);
        if (facture.getId() == null) {
            return createFacture(facture);
        }
        Facture result = factureRepository.save(facture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, facture.getId().toString()))
            .body(result);
    }

    /**
     * GET  /factures : get all the factures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of factures in body
     */
    @GetMapping("/factures")
    @Timed
    public ResponseEntity<List<Facture>> getAllFactures(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Factures");
        Page<Facture> page = factureRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/factures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /factures/:id : get the "id" facture.
     *
     * @param id the id of the facture to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the facture, or with status 404 (Not Found)
     */
    @GetMapping("/factures/{id}")
    @Timed
    public ResponseEntity<Facture> getFacture(@PathVariable Long id) {
        log.debug("REST request to get Facture : {}", id);
        Facture facture = factureRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(facture));
    }

    /**
     * DELETE  /factures/:id : delete the "id" facture.
     *
     * @param id the id of the facture to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/factures/{id}")
    @Timed
    public ResponseEntity<Void> deleteFacture(@PathVariable Long id) {
        log.debug("REST request to delete Facture : {}", id);
        factureRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
