package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.ProduitCategorie;

import com.tsoft.app.repository.ProduitCategorieRepository;
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
 * REST controller for managing ProduitCategorie.
 */
@RestController
@RequestMapping("/api")
public class ProduitCategorieResource {

    private final Logger log = LoggerFactory.getLogger(ProduitCategorieResource.class);

    private static final String ENTITY_NAME = "produitCategorie";
        
    private final ProduitCategorieRepository produitCategorieRepository;


    public ProduitCategorieResource(ProduitCategorieRepository produitCategorieRepository) {
        this.produitCategorieRepository = produitCategorieRepository;
    }

    /**
     * POST  /produit-categories : Create a new produitCategorie.
     *
     * @param produitCategorie the produitCategorie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produitCategorie, or with status 400 (Bad Request) if the produitCategorie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produit-categories")
    @Timed
    public ResponseEntity<ProduitCategorie> createProduitCategorie(@Valid @RequestBody ProduitCategorie produitCategorie) throws Exception {
        log.debug("REST request to save ProduitCategorie : {}", produitCategorie);
        if (produitCategorie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new produitCategorie cannot already have an ID")).body(null);
        }
        ProduitCategorie result = produitCategorieRepository.save(produitCategorie);
        return ResponseEntity.created(new URI("/api/produit-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produit-categories : Updates an existing produitCategorie.
     *
     * @param produitCategorie the produitCategorie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produitCategorie,
     * or with status 400 (Bad Request) if the produitCategorie is not valid,
     * or with status 500 (Internal Server Error) if the produitCategorie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produit-categories")
    @Timed
    public ResponseEntity<ProduitCategorie> updateProduitCategorie(@Valid @RequestBody ProduitCategorie produitCategorie) throws Exception {
        log.debug("REST request to update ProduitCategorie : {}", produitCategorie);
        if (produitCategorie.getId() == null) {
            return createProduitCategorie(produitCategorie);
        }
        ProduitCategorie result = produitCategorieRepository.save(produitCategorie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produitCategorie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produit-categories : get all the produitCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of produitCategories in body
     */
    @GetMapping("/produit-categories")
    @Timed
    public ResponseEntity<List<ProduitCategorie>> getAllProduitCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ProduitCategories");
        Page<ProduitCategorie> page = produitCategorieRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produit-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /produit-categories/:id : get the "id" produitCategorie.
     *
     * @param id the id of the produitCategorie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produitCategorie, or with status 404 (Not Found)
     */
    @GetMapping("/produit-categories/{id}")
    @Timed
    public ResponseEntity<ProduitCategorie> getProduitCategorie(@PathVariable Long id) {
        log.debug("REST request to get ProduitCategorie : {}", id);
        ProduitCategorie produitCategorie = produitCategorieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produitCategorie));
    }

    /**
     * DELETE  /produit-categories/:id : delete the "id" produitCategorie.
     *
     * @param id the id of the produitCategorie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produit-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduitCategorie(@PathVariable Long id) {
        log.debug("REST request to delete ProduitCategorie : {}", id);
        produitCategorieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
