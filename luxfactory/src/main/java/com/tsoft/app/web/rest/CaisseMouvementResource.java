package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.CaisseMouvement;

import com.tsoft.app.repository.CaisseMouvementRepository;
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
 * REST controller for managing CaisseMouvement.
 */
@RestController
@RequestMapping("/api")
public class CaisseMouvementResource {

    private final Logger log = LoggerFactory.getLogger(CaisseMouvementResource.class);

    private static final String ENTITY_NAME = "caisseMouvement";
        
    private final CaisseMouvementRepository caisseMouvementRepository;


    public CaisseMouvementResource(CaisseMouvementRepository caisseMouvementRepository) {
        this.caisseMouvementRepository = caisseMouvementRepository;
    }

    /**
     * POST  /caisse-mouvements : Create a new caisseMouvement.
     *
     * @param caisseMouvement the caisseMouvement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caisseMouvement, or with status 400 (Bad Request) if the caisseMouvement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caisse-mouvements")
    @Timed
    public ResponseEntity<CaisseMouvement> createCaisseMouvement(@Valid @RequestBody CaisseMouvement caisseMouvement) throws Exception {
        log.debug("REST request to save CaisseMouvement : {}", caisseMouvement);
        if (caisseMouvement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new caisseMouvement cannot already have an ID")).body(null);
        }
        CaisseMouvement result = caisseMouvementRepository.save(caisseMouvement);
        return ResponseEntity.created(new URI("/api/caisse-mouvements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /caisse-mouvements : Updates an existing caisseMouvement.
     *
     * @param caisseMouvement the caisseMouvement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caisseMouvement,
     * or with status 400 (Bad Request) if the caisseMouvement is not valid,
     * or with status 500 (Internal Server Error) if the caisseMouvement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/caisse-mouvements")
    @Timed
    public ResponseEntity<CaisseMouvement> updateCaisseMouvement(@Valid @RequestBody CaisseMouvement caisseMouvement) throws Exception {
        log.debug("REST request to update CaisseMouvement : {}", caisseMouvement);
        if (caisseMouvement.getId() == null) {
            return createCaisseMouvement(caisseMouvement);
        }
        CaisseMouvement result = caisseMouvementRepository.save(caisseMouvement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caisseMouvement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /caisse-mouvements : get all the caisseMouvements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of caisseMouvements in body
     */
    @GetMapping("/caisse-mouvements")
    @Timed
    public ResponseEntity<List<CaisseMouvement>> getAllCaisseMouvements(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CaisseMouvements");
        Page<CaisseMouvement> page = caisseMouvementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caisse-mouvements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /caisse-mouvements/:id : get the "id" caisseMouvement.
     *
     * @param id the id of the caisseMouvement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caisseMouvement, or with status 404 (Not Found)
     */
    @GetMapping("/caisse-mouvements/{id}")
    @Timed
    public ResponseEntity<CaisseMouvement> getCaisseMouvement(@PathVariable Long id) {
        log.debug("REST request to get CaisseMouvement : {}", id);
        CaisseMouvement caisseMouvement = caisseMouvementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caisseMouvement));
    }

    /**
     * DELETE  /caisse-mouvements/:id : delete the "id" caisseMouvement.
     *
     * @param id the id of the caisseMouvement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/caisse-mouvements/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaisseMouvement(@PathVariable Long id) {
        log.debug("REST request to delete CaisseMouvement : {}", id);
        caisseMouvementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
