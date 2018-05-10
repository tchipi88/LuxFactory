package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.ProduitFournisseur;

import com.tsoft.app.repository.ProduitFournisseurRepository;
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
 * REST controller for managing ProduitFournisseur.
 */
@RestController
@RequestMapping("/api")
public class ProduitFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(ProduitFournisseurResource.class);

    private static final String ENTITY_NAME = "produitFournisseur";
        
    private final ProduitFournisseurRepository produitFournisseurRepository;


    public ProduitFournisseurResource(ProduitFournisseurRepository produitFournisseurRepository) {
        this.produitFournisseurRepository = produitFournisseurRepository;
    }

    /**
     * POST  /produit-fournisseurs : Create a new produitFournisseur.
     *
     * @param produitFournisseur the produitFournisseur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produitFournisseur, or with status 400 (Bad Request) if the produitFournisseur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produit-fournisseurs")
    @Timed
    public ResponseEntity<ProduitFournisseur> createProduitFournisseur(@Valid @RequestBody ProduitFournisseur produitFournisseur) throws Exception {
        log.debug("REST request to save ProduitFournisseur : {}", produitFournisseur);
        if (produitFournisseur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new produitFournisseur cannot already have an ID")).body(null);
        }
        ProduitFournisseur result = produitFournisseurRepository.save(produitFournisseur);
        return ResponseEntity.created(new URI("/api/produit-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produit-fournisseurs : Updates an existing produitFournisseur.
     *
     * @param produitFournisseur the produitFournisseur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produitFournisseur,
     * or with status 400 (Bad Request) if the produitFournisseur is not valid,
     * or with status 500 (Internal Server Error) if the produitFournisseur couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produit-fournisseurs")
    @Timed
    public ResponseEntity<ProduitFournisseur> updateProduitFournisseur(@Valid @RequestBody ProduitFournisseur produitFournisseur) throws Exception {
        log.debug("REST request to update ProduitFournisseur : {}", produitFournisseur);
        if (produitFournisseur.getId() == null) {
            return createProduitFournisseur(produitFournisseur);
        }
        ProduitFournisseur result = produitFournisseurRepository.save(produitFournisseur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produitFournisseur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produit-fournisseurs : get all the produitFournisseurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of produitFournisseurs in body
     */
    @GetMapping("/produit-fournisseurs")
    @Timed
    public ResponseEntity<List<ProduitFournisseur>> getAllProduitFournisseurs(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ProduitFournisseurs");
        Page<ProduitFournisseur> page = produitFournisseurRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produit-fournisseurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /produit-fournisseurs/:id : get the "id" produitFournisseur.
     *
     * @param id the id of the produitFournisseur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produitFournisseur, or with status 404 (Not Found)
     */
    @GetMapping("/produit-fournisseurs/{id}")
    @Timed
    public ResponseEntity<ProduitFournisseur> getProduitFournisseur(@PathVariable Long id) {
        log.debug("REST request to get ProduitFournisseur : {}", id);
        ProduitFournisseur produitFournisseur = produitFournisseurRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produitFournisseur));
    }

    /**
     * DELETE  /produit-fournisseurs/:id : delete the "id" produitFournisseur.
     *
     * @param id the id of the produitFournisseur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produit-fournisseurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduitFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete ProduitFournisseur : {}", id);
        produitFournisseurRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
