package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Entrepot;

import com.tsoft.app.repository.EntrepotRepository;
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
 * REST controller for managing Entrepot.
 */
@RestController
@RequestMapping("/api")
public class EntrepotResource {

    private final Logger log = LoggerFactory.getLogger(EntrepotResource.class);

    private static final String ENTITY_NAME = "entrepot";
        
    private final EntrepotRepository entrepotRepository;


    public EntrepotResource(EntrepotRepository entrepotRepository) {
        this.entrepotRepository = entrepotRepository;
    }

    /**
     * POST  /entrepots : Create a new entrepot.
     *
     * @param entrepot the entrepot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entrepot, or with status 400 (Bad Request) if the entrepot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entrepots")
    @Timed
    public ResponseEntity<Entrepot> createEntrepot(@Valid @RequestBody Entrepot entrepot) throws Exception {
        log.debug("REST request to save Entrepot : {}", entrepot);
        if (entrepot.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new entrepot cannot already have an ID")).body(null);
        }
        Entrepot result = entrepotRepository.save(entrepot);
        return ResponseEntity.created(new URI("/api/entrepots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entrepots : Updates an existing entrepot.
     *
     * @param entrepot the entrepot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entrepot,
     * or with status 400 (Bad Request) if the entrepot is not valid,
     * or with status 500 (Internal Server Error) if the entrepot couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entrepots")
    @Timed
    public ResponseEntity<Entrepot> updateEntrepot(@Valid @RequestBody Entrepot entrepot) throws Exception {
        log.debug("REST request to update Entrepot : {}", entrepot);
        if (entrepot.getId() == null) {
            return createEntrepot(entrepot);
        }
        Entrepot result = entrepotRepository.save(entrepot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entrepot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entrepots : get all the entrepots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of entrepots in body
     */
    @GetMapping("/entrepots")
    @Timed
    public ResponseEntity<List<Entrepot>> getAllEntrepots(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Entrepots");
        Page<Entrepot> page = entrepotRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entrepots");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /entrepots/:id : get the "id" entrepot.
     *
     * @param id the id of the entrepot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entrepot, or with status 404 (Not Found)
     */
    @GetMapping("/entrepots/{id}")
    @Timed
    public ResponseEntity<Entrepot> getEntrepot(@PathVariable Long id) {
        log.debug("REST request to get Entrepot : {}", id);
        Entrepot entrepot = entrepotRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entrepot));
    }

    /**
     * DELETE  /entrepots/:id : delete the "id" entrepot.
     *
     * @param id the id of the entrepot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entrepots/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntrepot(@PathVariable Long id) {
        log.debug("REST request to delete Entrepot : {}", id);
        entrepotRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
