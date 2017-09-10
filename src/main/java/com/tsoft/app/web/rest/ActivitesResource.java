package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Activites;

import com.tsoft.app.repository.ActivitesRepository;
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
 * REST controller for managing Activites.
 */
@RestController
@RequestMapping("/api")
public class ActivitesResource {

    private final Logger log = LoggerFactory.getLogger(ActivitesResource.class);

    private static final String ENTITY_NAME = "activites";
        
    private final ActivitesRepository activitesRepository;


    public ActivitesResource(ActivitesRepository activitesRepository) {
        this.activitesRepository = activitesRepository;
    }

    /**
     * POST  /activitess : Create a new activites.
     *
     * @param activites the activites to create
     * @return the ResponseEntity with status 201 (Created) and with body the new activites, or with status 400 (Bad Request) if the activites has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/activitess")
    @Timed
    public ResponseEntity<Activites> createActivites(@Valid @RequestBody Activites activites) throws Exception {
        log.debug("REST request to save Activites : {}", activites);
        if (activites.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new activites cannot already have an ID")).body(null);
        }
        Activites result = activitesRepository.save(activites);
        return ResponseEntity.created(new URI("/api/activitess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /activitess : Updates an existing activites.
     *
     * @param activites the activites to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated activites,
     * or with status 400 (Bad Request) if the activites is not valid,
     * or with status 500 (Internal Server Error) if the activites couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/activitess")
    @Timed
    public ResponseEntity<Activites> updateActivites(@Valid @RequestBody Activites activites) throws Exception {
        log.debug("REST request to update Activites : {}", activites);
        if (activites.getId() == null) {
            return createActivites(activites);
        }
        Activites result = activitesRepository.save(activites);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, activites.getId().toString()))
            .body(result);
    }

    /**
     * GET  /activitess : get all the activitess.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of activitess in body
     */
    @GetMapping("/activitess")
    @Timed
    public ResponseEntity<List<Activites>> getAllActivitess(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Activitess");
        Page<Activites> page = activitesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/activitess");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /activitess/:id : get the "id" activites.
     *
     * @param id the id of the activites to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the activites, or with status 404 (Not Found)
     */
    @GetMapping("/activitess/{id}")
    @Timed
    public ResponseEntity<Activites> getActivites(@PathVariable Long id) {
        log.debug("REST request to get Activites : {}", id);
        Activites activites = activitesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(activites));
    }

    /**
     * DELETE  /activitess/:id : delete the "id" activites.
     *
     * @param id the id of the activites to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/activitess/{id}")
    @Timed
    public ResponseEntity<Void> deleteActivites(@PathVariable Long id) {
        log.debug("REST request to delete Activites : {}", id);
        activitesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   
    

}
