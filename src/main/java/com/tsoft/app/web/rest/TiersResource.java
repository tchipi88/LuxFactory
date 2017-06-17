package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Tiers;

import com.tsoft.app.repository.TiersRepository;
import com.tsoft.app.repository.search.TiersSearchRepository;
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
 * REST controller for managing Tiers.
 */
@RestController
@RequestMapping("/api")
public class TiersResource {

    private final Logger log = LoggerFactory.getLogger(TiersResource.class);

    private static final String ENTITY_NAME = "tiers";
        
    private final TiersRepository tiersRepository;

    private final TiersSearchRepository tiersSearchRepository;

    public TiersResource(TiersRepository tiersRepository, TiersSearchRepository tiersSearchRepository) {
        this.tiersRepository = tiersRepository;
        this.tiersSearchRepository = tiersSearchRepository;
    }

    /**
     * POST  /tierss : Create a new tiers.
     *
     * @param tiers the tiers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tiers, or with status 400 (Bad Request) if the tiers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tierss")
    @Timed
    public ResponseEntity<Tiers> createTiers(@Valid @RequestBody Tiers tiers) throws URISyntaxException {
        log.debug("REST request to save Tiers : {}", tiers);
        if (tiers.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tiers cannot already have an ID")).body(null);
        }
        Tiers result = tiersRepository.save(tiers);
        tiersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tierss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tierss : Updates an existing tiers.
     *
     * @param tiers the tiers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tiers,
     * or with status 400 (Bad Request) if the tiers is not valid,
     * or with status 500 (Internal Server Error) if the tiers couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tierss")
    @Timed
    public ResponseEntity<Tiers> updateTiers(@Valid @RequestBody Tiers tiers) throws URISyntaxException {
        log.debug("REST request to update Tiers : {}", tiers);
        if (tiers.getId() == null) {
            return createTiers(tiers);
        }
        Tiers result = tiersRepository.save(tiers);
        tiersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tiers.getId().toString()))
            .body(result);
    }

   /**
     * GET  /tierss : get all the tierss.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tierss in body
     */
    @GetMapping("/tierss")
    @Timed
    public ResponseEntity<List<Tiers>> getAllTierss(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Tierss");
        Page<Tiers> page = tiersRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tierss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tierss/:id : get the "id" tiers.
     *
     * @param id the id of the tiers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tiers, or with status 404 (Not Found)
     */
    @GetMapping("/tierss/{id}")
    @Timed
    public ResponseEntity<Tiers> getTiers(@PathVariable Long id) {
        log.debug("REST request to get Tiers : {}", id);
        Tiers tiers = tiersRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tiers));
    }

    /**
     * DELETE  /tierss/:id : delete the "id" tiers.
     *
     * @param id the id of the tiers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tierss/{id}")
    @Timed
    public ResponseEntity<Void> deleteTiers(@PathVariable Long id) {
        log.debug("REST request to delete Tiers : {}", id);
        tiersRepository.delete(id);
        tiersSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tierss?query=:query : search for the tiers corresponding
     * to the query.
     *
     * @param query the query of the tiers search 
     * @return the result of the search
     */
    @GetMapping("/_search/tierss")
    @Timed
    public ResponseEntity<List<Tiers>> searchTierss(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search Tierss for query {}", query);
      Page<Tiers> page = tiersSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tierss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




}
