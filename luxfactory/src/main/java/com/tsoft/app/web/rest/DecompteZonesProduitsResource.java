package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.DecompteZonesProduits;

import com.tsoft.app.repository.DecompteZonesProduitsRepository;
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
 * REST controller for managing DecompteZonesProduits.
 */
@RestController
@RequestMapping("/api")
public class DecompteZonesProduitsResource {

    private final Logger log = LoggerFactory.getLogger(DecompteZonesProduitsResource.class);

    private static final String ENTITY_NAME = "decompteZonesProduits";
        
    private final DecompteZonesProduitsRepository decompteZonesProduitsRepository;


    public DecompteZonesProduitsResource(DecompteZonesProduitsRepository decompteZonesProduitsRepository) {
        this.decompteZonesProduitsRepository = decompteZonesProduitsRepository;
    }

    /**
     * POST  /decompte-zones-produitss : Create a new decompteZonesProduits.
     *
     * @param decompteZonesProduits the decompteZonesProduits to create
     * @return the ResponseEntity with status 201 (Created) and with body the new decompteZonesProduits, or with status 400 (Bad Request) if the decompteZonesProduits has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/decompte-zones-produitss")
    @Timed
    public ResponseEntity<DecompteZonesProduits> createDecompteZonesProduits(@Valid @RequestBody DecompteZonesProduits decompteZonesProduits) throws Exception {
        log.debug("REST request to save DecompteZonesProduits : {}", decompteZonesProduits);
        if (decompteZonesProduits.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new decompteZonesProduits cannot already have an ID")).body(null);
        }
        DecompteZonesProduits result = decompteZonesProduitsRepository.save(decompteZonesProduits);
        return ResponseEntity.created(new URI("/api/decompte-zones-produitss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /decompte-zones-produitss : Updates an existing decompteZonesProduits.
     *
     * @param decompteZonesProduits the decompteZonesProduits to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated decompteZonesProduits,
     * or with status 400 (Bad Request) if the decompteZonesProduits is not valid,
     * or with status 500 (Internal Server Error) if the decompteZonesProduits couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/decompte-zones-produitss")
    @Timed
    public ResponseEntity<DecompteZonesProduits> updateDecompteZonesProduits(@Valid @RequestBody DecompteZonesProduits decompteZonesProduits) throws Exception {
        log.debug("REST request to update DecompteZonesProduits : {}", decompteZonesProduits);
        if (decompteZonesProduits.getId() == null) {
            return createDecompteZonesProduits(decompteZonesProduits);
        }
        DecompteZonesProduits result = decompteZonesProduitsRepository.save(decompteZonesProduits);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, decompteZonesProduits.getId().toString()))
            .body(result);
    }

    /**
     * GET  /decompte-zones-produitss : get all the decompteZonesProduitss.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of decompteZonesProduitss in body
     */
    @GetMapping("/decompte-zones-produitss")
    @Timed
    public ResponseEntity<List<DecompteZonesProduits>> getAllDecompteZonesProduitss(@ApiParam Pageable pageable) {
        log.debug("REST request to get all DecompteZonesProduitss");
        Page<DecompteZonesProduits> page = decompteZonesProduitsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/decompte-zones-produitss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /decompte-zones-produitss/:id : get the "id" decompteZonesProduits.
     *
     * @param id the id of the decompteZonesProduits to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the decompteZonesProduits, or with status 404 (Not Found)
     */
    @GetMapping("/decompte-zones-produitss/{id}")
    @Timed
    public ResponseEntity<DecompteZonesProduits> getDecompteZonesProduits(@PathVariable Long id) {
        log.debug("REST request to get DecompteZonesProduits : {}", id);
        DecompteZonesProduits decompteZonesProduits = decompteZonesProduitsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(decompteZonesProduits));
    }

    /**
     * DELETE  /decompte-zones-produitss/:id : delete the "id" decompteZonesProduits.
     *
     * @param id the id of the decompteZonesProduits to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/decompte-zones-produitss/{id}")
    @Timed
    public ResponseEntity<Void> deleteDecompteZonesProduits(@PathVariable Long id) {
        log.debug("REST request to delete DecompteZonesProduits : {}", id);
        decompteZonesProduitsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
