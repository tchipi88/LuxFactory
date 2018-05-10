package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Fournisseur;

import com.tsoft.app.repository.FournisseurRepository;
import com.tsoft.app.repository.search.FournisseurSearchRepository;
import com.tsoft.app.service.FournisseurService;
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

import static org.elasticsearch.index.query.QueryBuilders.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Fournisseur.
 */
@RestController
@RequestMapping("/api")
public class FournisseurResource {

     private final Logger log = LoggerFactory.getLogger(FournisseurResource.class);

    private static final String ENTITY_NAME = "fournisseur";
        
    private final FournisseurRepository fournisseurRepository;
    
    private final FournisseurService  fournisseurService;

    private final FournisseurSearchRepository fournisseurSearchRepository;

    public FournisseurResource(FournisseurRepository fournisseurRepository, FournisseurSearchRepository fournisseurSearchRepository,FournisseurService fournisseurService) {
        this.fournisseurRepository = fournisseurRepository;
        this.fournisseurSearchRepository = fournisseurSearchRepository;
        this.fournisseurService=fournisseurService;
    }

    /**
     * POST  /fournisseurs : Create a new fournisseur.
     *
     * @param fournisseur the fournisseur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fournisseur, or with status 400 (Bad Request) if the fournisseur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fournisseurs")
    @Timed
    public ResponseEntity<Fournisseur> createFournisseur(@Valid @RequestBody Fournisseur fournisseur) throws Exception {
        log.debug("REST request to save Fournisseur : {}", fournisseur);
        if (fournisseur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fournisseur cannot already have an ID")).body(null);
        }
        Fournisseur result = fournisseurService.create(fournisseur);
        fournisseurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fournisseurs : Updates an existing fournisseur.
     *
     * @param fournisseur the fournisseur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fournisseur,
     * or with status 400 (Bad Request) if the fournisseur is not valid,
     * or with status 500 (Internal Server Error) if the fournisseur couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fournisseurs")
    @Timed
    public ResponseEntity<Fournisseur> updateFournisseur(@Valid @RequestBody Fournisseur fournisseur) throws Exception {
        log.debug("REST request to update Fournisseur : {}", fournisseur);
        if (fournisseur.getId() == null) {
            return createFournisseur(fournisseur);
        }
        Fournisseur result = fournisseurService.update(fournisseur);
        fournisseurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fournisseur.getId().toString()))
            .body(result);
    }

   /**
     * GET  /fournisseurs : get all the fournisseurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fournisseurs in body
     */
    @GetMapping("/fournisseurs")
    @Timed
    public ResponseEntity<List<Fournisseur>> getAllFournisseurs(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Fournisseurs");
        Page<Fournisseur> page = fournisseurRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fournisseurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fournisseurs/:id : get the "id" fournisseur.
     *
     * @param id the id of the fournisseur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fournisseur, or with status 404 (Not Found)
     */
    @GetMapping("/fournisseurs/{id}")
    @Timed
    public ResponseEntity<Fournisseur> getFournisseur(@PathVariable Long id) {
        log.debug("REST request to get Fournisseur : {}", id);
        Fournisseur fournisseur = fournisseurRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fournisseur));
    }

    /**
     * DELETE  /fournisseurs/:id : delete the "id" fournisseur.
     *
     * @param id the id of the fournisseur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fournisseurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete Fournisseur : {}", id);
        fournisseurRepository.delete(id);
        fournisseurSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   /**
     * SEARCH  /_search/fournisseurs?query=:query : search for the fournisseur corresponding
     * to the query.
     *
     * @param query the query of the fournisseur search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/fournisseurs")
    @Timed
    public ResponseEntity<List<Fournisseur>> searchFournisseurs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Fournisseurs for query {}", query);
        Page<Fournisseur> page = fournisseurSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/fournisseurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }





}
