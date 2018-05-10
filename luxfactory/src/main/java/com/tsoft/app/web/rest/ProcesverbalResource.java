package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Procesverbal;

import com.tsoft.app.repository.ProcesverbalRepository;
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
 * REST controller for managing Procesverbal.
 */
@RestController
@RequestMapping("/api")
public class ProcesverbalResource {

    private final Logger log = LoggerFactory.getLogger(ProcesverbalResource.class);

    private static final String ENTITY_NAME = "procesverbal";
        
    private final ProcesverbalRepository procesverbalRepository;


    public ProcesverbalResource(ProcesverbalRepository procesverbalRepository) {
        this.procesverbalRepository = procesverbalRepository;
    }

    /**
     * POST  /procesverbals : Create a new procesverbal.
     *
     * @param procesverbal the procesverbal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procesverbal, or with status 400 (Bad Request) if the procesverbal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procesverbals")
    @Timed
    public ResponseEntity<Procesverbal> createProcesverbal(@Valid @RequestBody Procesverbal procesverbal) throws Exception {
        log.debug("REST request to save Procesverbal : {}", procesverbal);
        if (procesverbal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new procesverbal cannot already have an ID")).body(null);
        }
        Procesverbal result = procesverbalRepository.save(procesverbal);
        return ResponseEntity.created(new URI("/api/procesverbals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procesverbals : Updates an existing procesverbal.
     *
     * @param procesverbal the procesverbal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procesverbal,
     * or with status 400 (Bad Request) if the procesverbal is not valid,
     * or with status 500 (Internal Server Error) if the procesverbal couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procesverbals")
    @Timed
    public ResponseEntity<Procesverbal> updateProcesverbal(@Valid @RequestBody Procesverbal procesverbal) throws Exception {
        log.debug("REST request to update Procesverbal : {}", procesverbal);
        if (procesverbal.getId() == null) {
            return createProcesverbal(procesverbal);
        }
        Procesverbal result = procesverbalRepository.save(procesverbal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procesverbal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procesverbals : get all the procesverbals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of procesverbals in body
     */
    @GetMapping("/procesverbals")
    @Timed
    public ResponseEntity<List<Procesverbal>> getAllProcesverbals(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Procesverbals");
        Page<Procesverbal> page = procesverbalRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/procesverbals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /procesverbals/:id : get the "id" procesverbal.
     *
     * @param id the id of the procesverbal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procesverbal, or with status 404 (Not Found)
     */
    @GetMapping("/procesverbals/{id}")
    @Timed
    public ResponseEntity<Procesverbal> getProcesverbal(@PathVariable Long id) {
        log.debug("REST request to get Procesverbal : {}", id);
        Procesverbal procesverbal = procesverbalRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(procesverbal));
    }

    /**
     * DELETE  /procesverbals/:id : delete the "id" procesverbal.
     *
     * @param id the id of the procesverbal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procesverbals/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcesverbal(@PathVariable Long id) {
        log.debug("REST request to delete Procesverbal : {}", id);
        procesverbalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
