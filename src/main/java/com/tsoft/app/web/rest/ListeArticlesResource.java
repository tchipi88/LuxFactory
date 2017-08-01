package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.ListeArticles;

import com.tsoft.app.repository.ListeArticlesRepository;
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
 * REST controller for managing ListeArticles.
 */
@RestController
@RequestMapping("/api")
public class ListeArticlesResource {

    private final Logger log = LoggerFactory.getLogger(ListeArticlesResource.class);

    private static final String ENTITY_NAME = "listeArticles";
        
    private final ListeArticlesRepository listeArticlesRepository;


    public ListeArticlesResource(ListeArticlesRepository listeArticlesRepository) {
        this.listeArticlesRepository = listeArticlesRepository;
    }

    /**
     * POST  /liste-articless : Create a new listeArticles.
     *
     * @param listeArticles the listeArticles to create
     * @return the ResponseEntity with status 201 (Created) and with body the new listeArticles, or with status 400 (Bad Request) if the listeArticles has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/liste-articless")
    @Timed
    public ResponseEntity<ListeArticles> createListeArticles(@Valid @RequestBody ListeArticles listeArticles) throws Exception {
        log.debug("REST request to save ListeArticles : {}", listeArticles);
        if (listeArticles.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new listeArticles cannot already have an ID")).body(null);
        }
        ListeArticles result = listeArticlesRepository.save(listeArticles);
        return ResponseEntity.created(new URI("/api/liste-articless/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /liste-articless : Updates an existing listeArticles.
     *
     * @param listeArticles the listeArticles to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated listeArticles,
     * or with status 400 (Bad Request) if the listeArticles is not valid,
     * or with status 500 (Internal Server Error) if the listeArticles couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/liste-articless")
    @Timed
    public ResponseEntity<ListeArticles> updateListeArticles(@Valid @RequestBody ListeArticles listeArticles) throws Exception {
        log.debug("REST request to update ListeArticles : {}", listeArticles);
        if (listeArticles.getId() == null) {
            return createListeArticles(listeArticles);
        }
        ListeArticles result = listeArticlesRepository.save(listeArticles);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, listeArticles.getId().toString()))
            .body(result);
    }

    /**
     * GET  /liste-articless : get all the listeArticless.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of listeArticless in body
     */
    @GetMapping("/liste-articless")
    @Timed
    public ResponseEntity<List<ListeArticles>> getAllListeArticless(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ListeArticless");
        Page<ListeArticles> page = listeArticlesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/liste-articless");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /liste-articless/:id : get the "id" listeArticles.
     *
     * @param id the id of the listeArticles to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the listeArticles, or with status 404 (Not Found)
     */
    @GetMapping("/liste-articless/{id}")
    @Timed
    public ResponseEntity<ListeArticles> getListeArticles(@PathVariable Long id) {
        log.debug("REST request to get ListeArticles : {}", id);
        ListeArticles listeArticles = listeArticlesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(listeArticles));
    }

    /**
     * DELETE  /liste-articless/:id : delete the "id" listeArticles.
     *
     * @param id the id of the listeArticles to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/liste-articless/{id}")
    @Timed
    public ResponseEntity<Void> deleteListeArticles(@PathVariable Long id) {
        log.debug("REST request to delete ListeArticles : {}", id);
        listeArticlesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
