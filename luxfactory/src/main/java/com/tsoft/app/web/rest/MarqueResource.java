package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Marque;

import com.tsoft.app.repository.MarqueRepository;
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
 * REST controller for managing Marque.
 */
@RestController
@RequestMapping("/api")
public class MarqueResource {

    private final Logger log = LoggerFactory.getLogger(MarqueResource.class);

    private static final String ENTITY_NAME = "marque";
        
    private final MarqueRepository marqueRepository;


    public MarqueResource(MarqueRepository marqueRepository) {
        this.marqueRepository = marqueRepository;
    }

    /**
     * POST  /marques : Create a new marque.
     *
     * @param marque the marque to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marque, or with status 400 (Bad Request) if the marque has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/marques")
    @Timed
    public ResponseEntity<Marque> createMarque(@Valid @RequestBody Marque marque) throws Exception {
        log.debug("REST request to save Marque : {}", marque);
        if (marque.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marque cannot already have an ID")).body(null);
        }
        Marque result = marqueRepository.save(marque);
        return ResponseEntity.created(new URI("/api/marques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /marques : Updates an existing marque.
     *
     * @param marque the marque to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marque,
     * or with status 400 (Bad Request) if the marque is not valid,
     * or with status 500 (Internal Server Error) if the marque couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/marques")
    @Timed
    public ResponseEntity<Marque> updateMarque(@Valid @RequestBody Marque marque) throws Exception {
        log.debug("REST request to update Marque : {}", marque);
        if (marque.getId() == null) {
            return createMarque(marque);
        }
        Marque result = marqueRepository.save(marque);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marque.getId().toString()))
            .body(result);
    }

    /**
     * GET  /marques : get all the marques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of marques in body
     */
    @GetMapping("/marques")
    @Timed
    public ResponseEntity<List<Marque>> getAllMarques(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Marques");
        Page<Marque> page = marqueRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/marques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /marques/:id : get the "id" marque.
     *
     * @param id the id of the marque to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marque, or with status 404 (Not Found)
     */
    @GetMapping("/marques/{id}")
    @Timed
    public ResponseEntity<Marque> getMarque(@PathVariable Long id) {
        log.debug("REST request to get Marque : {}", id);
        Marque marque = marqueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marque));
    }

    /**
     * DELETE  /marques/:id : delete the "id" marque.
     *
     * @param id the id of the marque to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/marques/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarque(@PathVariable Long id) {
        log.debug("REST request to delete Marque : {}", id);
        marqueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
