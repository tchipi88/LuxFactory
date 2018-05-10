package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.EntrepotProduit;

import com.tsoft.app.repository.EntrepotProduitRepository;
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
 * REST controller for managing EntrepotProduit.
 */
@RestController
@RequestMapping("/api")
public class EntrepotProduitResource {

    private final Logger log = LoggerFactory.getLogger(EntrepotProduitResource.class);

    private static final String ENTITY_NAME = "entrepotProduit";
        
    private final EntrepotProduitRepository entrepotProduitRepository;


    public EntrepotProduitResource(EntrepotProduitRepository entrepotProduitRepository) {
        this.entrepotProduitRepository = entrepotProduitRepository;
    }

    /**
     * POST  /entrepot-produits : Create a new entrepotProduit.
     *
     * @param entrepotProduit the entrepotProduit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entrepotProduit, or with status 400 (Bad Request) if the entrepotProduit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entrepot-produits")
    @Timed
    public ResponseEntity<EntrepotProduit> createEntrepotProduit(@Valid @RequestBody EntrepotProduit entrepotProduit) throws Exception {
        log.debug("REST request to save EntrepotProduit : {}", entrepotProduit);
        if (entrepotProduit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entrepotProduit cannot already have an ID")).body(null);
        }
        EntrepotProduit result = entrepotProduitRepository.save(entrepotProduit);
        return ResponseEntity.created(new URI("/api/entrepot-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entrepot-produits : Updates an existing entrepotProduit.
     *
     * @param entrepotProduit the entrepotProduit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entrepotProduit,
     * or with status 400 (Bad Request) if the entrepotProduit is not valid,
     * or with status 500 (Internal Server Error) if the entrepotProduit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entrepot-produits")
    @Timed
    public ResponseEntity<EntrepotProduit> updateEntrepotProduit(@Valid @RequestBody EntrepotProduit entrepotProduit) throws Exception {
        log.debug("REST request to update EntrepotProduit : {}", entrepotProduit);
        if (entrepotProduit.getId() == null) {
            return createEntrepotProduit(entrepotProduit);
        }
        EntrepotProduit result = entrepotProduitRepository.save(entrepotProduit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entrepotProduit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entrepot-produits : get all the entrepotProduits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entrepotProduits in body
     */
    @GetMapping("/entrepot-produits")
    @Timed
    public ResponseEntity<List<EntrepotProduit>> getAllEntrepotProduits(@ApiParam Pageable pageable) {
        log.debug("REST request to get all EntrepotProduits");
        Page<EntrepotProduit> page = entrepotProduitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entrepot-produits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /entrepot-produits/:id : get the "id" entrepotProduit.
     *
     * @param id the id of the entrepotProduit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entrepotProduit, or with status 404 (Not Found)
     */
    @GetMapping("/entrepot-produits/{id}")
    @Timed
    public ResponseEntity<EntrepotProduit> getEntrepotProduit(@PathVariable Long id) {
        log.debug("REST request to get EntrepotProduit : {}", id);
        EntrepotProduit entrepotProduit = entrepotProduitRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entrepotProduit));
    }

    /**
     * DELETE  /entrepot-produits/:id : delete the "id" entrepotProduit.
     *
     * @param id the id of the entrepotProduit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entrepot-produits/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntrepotProduit(@PathVariable Long id) {
        log.debug("REST request to delete EntrepotProduit : {}", id);
        entrepotProduitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
