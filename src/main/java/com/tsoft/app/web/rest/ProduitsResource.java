package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Produits;

import com.tsoft.app.repository.ProduitsRepository;
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
 * REST controller for managing Produits.
 */
@RestController
@RequestMapping("/api")
public class ProduitsResource {

    private final Logger log = LoggerFactory.getLogger(ProduitsResource.class);

    private static final String ENTITY_NAME = "produits";
        
    private final ProduitsRepository produitsRepository;


    public ProduitsResource(ProduitsRepository produitsRepository) {
        this.produitsRepository = produitsRepository;
    }

    /**
     * POST  /produitss : Create a new produits.
     *
     * @param produits the produits to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produits, or with status 400 (Bad Request) if the produits has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produitss")
    @Timed
    public ResponseEntity<Produits> createProduits(@Valid @RequestBody Produits produits) throws Exception {
        log.debug("REST request to save Produits : {}", produits);
        if (produits.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new produits cannot already have an ID")).body(null);
        }
        Produits result = produitsRepository.save(produits);
        return ResponseEntity.created(new URI("/api/produitss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produitss : Updates an existing produits.
     *
     * @param produits the produits to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produits,
     * or with status 400 (Bad Request) if the produits is not valid,
     * or with status 500 (Internal Server Error) if the produits couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produitss")
    @Timed
    public ResponseEntity<Produits> updateProduits(@Valid @RequestBody Produits produits) throws Exception {
        log.debug("REST request to update Produits : {}", produits);
        if (produits.getId() == null) {
            return createProduits(produits);
        }
        Produits result = produitsRepository.save(produits);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produits.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produitss : get all the produitss.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of produitss in body
     */
    @GetMapping("/produitss")
    @Timed
    public ResponseEntity<List<Produits>> getAllProduitss(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Produitss");
        Page<Produits> page = produitsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produitss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /produitss/:id : get the "id" produits.
     *
     * @param id the id of the produits to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produits, or with status 404 (Not Found)
     */
    @GetMapping("/produitss/{id}")
    @Timed
    public ResponseEntity<Produits> getProduits(@PathVariable Long id) {
        log.debug("REST request to get Produits : {}", id);
        Produits produits = produitsRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produits));
    }

    /**
     * DELETE  /produitss/:id : delete the "id" produits.
     *
     * @param id the id of the produits to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produitss/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduits(@PathVariable Long id) {
        log.debug("REST request to delete Produits : {}", id);
        produitsRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
