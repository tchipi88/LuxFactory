package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.CompteAnalytique;

import com.tsoft.app.repository.CompteAnalytiqueRepository;
import com.tsoft.app.repository.search.CompteAnalytiqueSearchRepository;
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
 * REST controller for managing CompteAnalytique.
 */
@RestController
@RequestMapping("/api")
public class CompteAnalytiqueResource {

    private final Logger log = LoggerFactory.getLogger(CompteAnalytiqueResource.class);

    private static final String ENTITY_NAME = "compteAnalytique";
        
    private final CompteAnalytiqueRepository compteAnalytiqueRepository;

    private final CompteAnalytiqueSearchRepository compteAnalytiqueSearchRepository;

    public CompteAnalytiqueResource(CompteAnalytiqueRepository compteAnalytiqueRepository, CompteAnalytiqueSearchRepository compteAnalytiqueSearchRepository) {
        this.compteAnalytiqueRepository = compteAnalytiqueRepository;
        this.compteAnalytiqueSearchRepository = compteAnalytiqueSearchRepository;
    }

    /**
     * POST  /compte-analytiques : Create a new compteAnalytique.
     *
     * @param compteAnalytique the compteAnalytique to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compteAnalytique, or with status 400 (Bad Request) if the compteAnalytique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compte-analytiques")
    @Timed
    public ResponseEntity<CompteAnalytique> createCompteAnalytique(@Valid @RequestBody CompteAnalytique compteAnalytique) throws URISyntaxException {
        log.debug("REST request to save CompteAnalytique : {}", compteAnalytique);
        if (compteAnalytique.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new compteAnalytique cannot already have an ID")).body(null);
        }
        CompteAnalytique result = compteAnalytiqueRepository.save(compteAnalytique);
        compteAnalytiqueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/compte-analytiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compte-analytiques : Updates an existing compteAnalytique.
     *
     * @param compteAnalytique the compteAnalytique to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compteAnalytique,
     * or with status 400 (Bad Request) if the compteAnalytique is not valid,
     * or with status 500 (Internal Server Error) if the compteAnalytique couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-analytiques")
    @Timed
    public ResponseEntity<CompteAnalytique> updateCompteAnalytique(@Valid @RequestBody CompteAnalytique compteAnalytique) throws URISyntaxException {
        log.debug("REST request to update CompteAnalytique : {}", compteAnalytique);
        if (compteAnalytique.getId() == null) {
            return createCompteAnalytique(compteAnalytique);
        }
        CompteAnalytique result = compteAnalytiqueRepository.save(compteAnalytique);
        compteAnalytiqueSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteAnalytique.getId().toString()))
            .body(result);
    }

   /**
     * GET  /compte-analytiques : get all the compteAnalytiques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compteAnalytiques in body
     */
    @GetMapping("/compte-analytiques")
    @Timed
    public ResponseEntity<List<CompteAnalytique>> getAllCompteAnalytiques(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CompteAnalytiques");
        Page<CompteAnalytique> page = compteAnalytiqueRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-analytiques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /compte-analytiques/:id : get the "id" compteAnalytique.
     *
     * @param id the id of the compteAnalytique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compteAnalytique, or with status 404 (Not Found)
     */
    @GetMapping("/compte-analytiques/{id}")
    @Timed
    public ResponseEntity<CompteAnalytique> getCompteAnalytique(@PathVariable Long id) {
        log.debug("REST request to get CompteAnalytique : {}", id);
        CompteAnalytique compteAnalytique = compteAnalytiqueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compteAnalytique));
    }

    /**
     * DELETE  /compte-analytiques/:id : delete the "id" compteAnalytique.
     *
     * @param id the id of the compteAnalytique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compte-analytiques/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompteAnalytique(@PathVariable Long id) {
        log.debug("REST request to delete CompteAnalytique : {}", id);
        compteAnalytiqueRepository.delete(id);
        compteAnalytiqueSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/compte-analytiques?query=:query : search for the compteAnalytique corresponding
     * to the query.
     *
     * @param query the query of the compteAnalytique search 
     * @return the result of the search
     */
    @GetMapping("/_search/compte-analytiques")
    @Timed
    public ResponseEntity<List<CompteAnalytique>> searchCompteAnalytiques(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search CompteAnalytiques for query {}", query);
      Page<CompteAnalytique> page = compteAnalytiqueSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/compte-analytiques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




}
