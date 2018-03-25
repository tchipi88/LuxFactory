package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.StatutZonesProduit;

import com.tsoft.app.repository.StatutZonesProduitRepository;
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
 * REST controller for managing StatutZonesProduit.
 */
@RestController
@RequestMapping("/api")
public class StatutZonesProduitResource {

    private final Logger log = LoggerFactory.getLogger(StatutZonesProduitResource.class);

    private static final String ENTITY_NAME = "statutZonesProduit";
        
    private final StatutZonesProduitRepository statutZonesProduitRepository;


    public StatutZonesProduitResource(StatutZonesProduitRepository statutZonesProduitRepository) {
        this.statutZonesProduitRepository = statutZonesProduitRepository;
    }

    /**
     * POST  /statut-zones-produits : Create a new statutZonesProduit.
     *
     * @param statutZonesProduit the statutZonesProduit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new statutZonesProduit, or with status 400 (Bad Request) if the statutZonesProduit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/statut-zones-produits")
    @Timed
    public ResponseEntity<StatutZonesProduit> createStatutZonesProduit(@Valid @RequestBody StatutZonesProduit statutZonesProduit) throws Exception {
        log.debug("REST request to save StatutZonesProduit : {}", statutZonesProduit);
        if (statutZonesProduit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new statutZonesProduit cannot already have an ID")).body(null);
        }
        StatutZonesProduit result = statutZonesProduitRepository.save(statutZonesProduit);
        return ResponseEntity.created(new URI("/api/statut-zones-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /statut-zones-produits : Updates an existing statutZonesProduit.
     *
     * @param statutZonesProduit the statutZonesProduit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated statutZonesProduit,
     * or with status 400 (Bad Request) if the statutZonesProduit is not valid,
     * or with status 500 (Internal Server Error) if the statutZonesProduit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/statut-zones-produits")
    @Timed
    public ResponseEntity<StatutZonesProduit> updateStatutZonesProduit(@Valid @RequestBody StatutZonesProduit statutZonesProduit) throws Exception {
        log.debug("REST request to update StatutZonesProduit : {}", statutZonesProduit);
        if (statutZonesProduit.getId() == null) {
            return createStatutZonesProduit(statutZonesProduit);
        }
        StatutZonesProduit result = statutZonesProduitRepository.save(statutZonesProduit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, statutZonesProduit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /statut-zones-produits : get all the statutZonesProduits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of statutZonesProduits in body
     */
    @GetMapping("/statut-zones-produits")
    @Timed
    public ResponseEntity<List<StatutZonesProduit>> getAllStatutZonesProduits(@ApiParam Pageable pageable) {
        log.debug("REST request to get all StatutZonesProduits");
        Page<StatutZonesProduit> page = statutZonesProduitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/statut-zones-produits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /statut-zones-produits/:id : get the "id" statutZonesProduit.
     *
     * @param id the id of the statutZonesProduit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the statutZonesProduit, or with status 404 (Not Found)
     */
    @GetMapping("/statut-zones-produits/{id}")
    @Timed
    public ResponseEntity<StatutZonesProduit> getStatutZonesProduit(@PathVariable Long id) {
        log.debug("REST request to get StatutZonesProduit : {}", id);
        StatutZonesProduit statutZonesProduit = statutZonesProduitRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(statutZonesProduit));
    }

    /**
     * DELETE  /statut-zones-produits/:id : delete the "id" statutZonesProduit.
     *
     * @param id the id of the statutZonesProduit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/statut-zones-produits/{id}")
    @Timed
    public ResponseEntity<Void> deleteStatutZonesProduit(@PathVariable Long id) {
        log.debug("REST request to delete StatutZonesProduit : {}", id);
        statutZonesProduitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
