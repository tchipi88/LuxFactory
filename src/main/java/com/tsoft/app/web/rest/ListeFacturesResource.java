package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.ListeFactures;

import com.tsoft.app.repository.ListeFacturesRepository;
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
 * REST controller for managing ListeFactures.
 */
@RestController
@RequestMapping("/api")
public class ListeFacturesResource {

    private final Logger log = LoggerFactory.getLogger(ListeFacturesResource.class);

    private static final String ENTITY_NAME = "listeFactures";
        
    private final ListeFacturesRepository listeFacturesRepository;


    public ListeFacturesResource(ListeFacturesRepository listeFacturesRepository) {
        this.listeFacturesRepository = listeFacturesRepository;
    }

    /**
     * POST  /liste-facturess : Create a new listeFactures.
     *
     * @param listeFactures the listeFactures to create
     * @return the ResponseEntity with status 201 (Created) and with body the new listeFactures, or with status 400 (Bad Request) if the listeFactures has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/liste-facturess")
    @Timed
    public ResponseEntity<ListeFactures> createListeFactures(@Valid @RequestBody ListeFactures listeFactures) throws Exception {
        log.debug("REST request to save ListeFactures : {}", listeFactures);
        if (listeFactures.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new listeFactures cannot already have an ID")).body(null);
        }
        ListeFactures result = listeFacturesRepository.save(listeFactures);
        return ResponseEntity.created(new URI("/api/liste-facturess/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /liste-facturess : Updates an existing listeFactures.
     *
     * @param listeFactures the listeFactures to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated listeFactures,
     * or with status 400 (Bad Request) if the listeFactures is not valid,
     * or with status 500 (Internal Server Error) if the listeFactures couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/liste-facturess")
    @Timed
    public ResponseEntity<ListeFactures> updateListeFactures(@Valid @RequestBody ListeFactures listeFactures) throws Exception {
        log.debug("REST request to update ListeFactures : {}", listeFactures);
        if (listeFactures.getId() == null) {
            return createListeFactures(listeFactures);
        }
        ListeFactures result = listeFacturesRepository.save(listeFactures);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, listeFactures.getId().toString()))
            .body(result);
    }

    /**
     * GET  /liste-facturess : get all the listeFacturess.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of listeFacturess in body
     */
    @GetMapping("/liste-facturess")
    @Timed
    public ResponseEntity<List<ListeFactures>> getAllListeFacturess(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ListeFacturess");
        Page<ListeFactures> page = listeFacturesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/liste-facturess");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /liste-facturess/:id : get the "id" listeFactures.
     *
     * @param id the id of the listeFactures to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the listeFactures, or with status 404 (Not Found)
     */
    @GetMapping("/liste-facturess/{id}")
    @Timed
    public ResponseEntity<ListeFactures> getListeFactures(@PathVariable Long id) {
        log.debug("REST request to get ListeFactures : {}", id);
        ListeFactures listeFactures = listeFacturesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(listeFactures));
    }

    /**
     * DELETE  /liste-facturess/:id : delete the "id" listeFactures.
     *
     * @param id the id of the listeFactures to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/liste-facturess/{id}")
    @Timed
    public ResponseEntity<Void> deleteListeFactures(@PathVariable Long id) {
        log.debug("REST request to delete ListeFactures : {}", id);
        listeFacturesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
