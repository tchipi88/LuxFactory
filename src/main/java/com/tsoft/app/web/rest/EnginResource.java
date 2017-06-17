package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Engin;

import com.tsoft.app.repository.EnginRepository;
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
 * REST controller for managing Engin.
 */
@RestController
@RequestMapping("/api")
public class EnginResource {

    private final Logger log = LoggerFactory.getLogger(EnginResource.class);

    private static final String ENTITY_NAME = "engin";
        
    private final EnginRepository enginRepository;


    public EnginResource(EnginRepository enginRepository) {
        this.enginRepository = enginRepository;
    }

    /**
     * POST  /engins : Create a new engin.
     *
     * @param engin the engin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new engin, or with status 400 (Bad Request) if the engin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/engins")
    @Timed
    public ResponseEntity<Engin> createEngin(@Valid @RequestBody Engin engin) throws Exception {
        log.debug("REST request to save Engin : {}", engin);
        if (engin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new engin cannot already have an ID")).body(null);
        }
        Engin result = enginRepository.save(engin);
        return ResponseEntity.created(new URI("/api/engins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /engins : Updates an existing engin.
     *
     * @param engin the engin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated engin,
     * or with status 400 (Bad Request) if the engin is not valid,
     * or with status 500 (Internal Server Error) if the engin couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/engins")
    @Timed
    public ResponseEntity<Engin> updateEngin(@Valid @RequestBody Engin engin) throws Exception {
        log.debug("REST request to update Engin : {}", engin);
        if (engin.getId() == null) {
            return createEngin(engin);
        }
        Engin result = enginRepository.save(engin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, engin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /engins : get all the engins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of engins in body
     */
    @GetMapping("/engins")
    @Timed
    public ResponseEntity<List<Engin>> getAllEngins(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Engins");
        Page<Engin> page = enginRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/engins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /engins/:id : get the "id" engin.
     *
     * @param id the id of the engin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the engin, or with status 404 (Not Found)
     */
    @GetMapping("/engins/{id}")
    @Timed
    public ResponseEntity<Engin> getEngin(@PathVariable Long id) {
        log.debug("REST request to get Engin : {}", id);
        Engin engin = enginRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(engin));
    }

    /**
     * DELETE  /engins/:id : delete the "id" engin.
     *
     * @param id the id of the engin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/engins/{id}")
    @Timed
    public ResponseEntity<Void> deleteEngin(@PathVariable Long id) {
        log.debug("REST request to delete Engin : {}", id);
        enginRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
