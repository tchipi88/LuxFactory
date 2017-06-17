package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.ReleveEngin;

import com.tsoft.app.repository.ReleveEnginRepository;
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
 * REST controller for managing ReleveEngin.
 */
@RestController
@RequestMapping("/api")
public class ReleveEnginResource {

    private final Logger log = LoggerFactory.getLogger(ReleveEnginResource.class);

    private static final String ENTITY_NAME = "releveEngin";
        
    private final ReleveEnginRepository releveEnginRepository;


    public ReleveEnginResource(ReleveEnginRepository releveEnginRepository) {
        this.releveEnginRepository = releveEnginRepository;
    }

    /**
     * POST  /releve-engins : Create a new releveEngin.
     *
     * @param releveEngin the releveEngin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new releveEngin, or with status 400 (Bad Request) if the releveEngin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/releve-engins")
    @Timed
    public ResponseEntity<ReleveEngin> createReleveEngin(@Valid @RequestBody ReleveEngin releveEngin) throws Exception {
        log.debug("REST request to save ReleveEngin : {}", releveEngin);
        if (releveEngin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new releveEngin cannot already have an ID")).body(null);
        }
        ReleveEngin result = releveEnginRepository.save(releveEngin);
        return ResponseEntity.created(new URI("/api/releve-engins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /releve-engins : Updates an existing releveEngin.
     *
     * @param releveEngin the releveEngin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated releveEngin,
     * or with status 400 (Bad Request) if the releveEngin is not valid,
     * or with status 500 (Internal Server Error) if the releveEngin couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/releve-engins")
    @Timed
    public ResponseEntity<ReleveEngin> updateReleveEngin(@Valid @RequestBody ReleveEngin releveEngin) throws Exception {
        log.debug("REST request to update ReleveEngin : {}", releveEngin);
        if (releveEngin.getId() == null) {
            return createReleveEngin(releveEngin);
        }
        ReleveEngin result = releveEnginRepository.save(releveEngin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, releveEngin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /releve-engins : get all the releveEngins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of releveEngins in body
     */
    @GetMapping("/releve-engins")
    @Timed
    public ResponseEntity<List<ReleveEngin>> getAllReleveEngins(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ReleveEngins");
        Page<ReleveEngin> page = releveEnginRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/releve-engins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /releve-engins/:id : get the "id" releveEngin.
     *
     * @param id the id of the releveEngin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the releveEngin, or with status 404 (Not Found)
     */
    @GetMapping("/releve-engins/{id}")
    @Timed
    public ResponseEntity<ReleveEngin> getReleveEngin(@PathVariable Long id) {
        log.debug("REST request to get ReleveEngin : {}", id);
        ReleveEngin releveEngin = releveEnginRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(releveEngin));
    }

    /**
     * DELETE  /releve-engins/:id : delete the "id" releveEngin.
     *
     * @param id the id of the releveEngin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/releve-engins/{id}")
    @Timed
    public ResponseEntity<Void> deleteReleveEngin(@PathVariable Long id) {
        log.debug("REST request to delete ReleveEngin : {}", id);
        releveEnginRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
