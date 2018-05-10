package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.CaisseOuverture;

import com.tsoft.app.repository.CaisseOuvertureRepository;
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
 * REST controller for managing CaisseOuverture.
 */
@RestController
@RequestMapping("/api")
public class CaisseOuvertureResource {

    private final Logger log = LoggerFactory.getLogger(CaisseOuvertureResource.class);

    private static final String ENTITY_NAME = "caisseOuverture";
        
    private final CaisseOuvertureRepository caisseOuvertureRepository;


    public CaisseOuvertureResource(CaisseOuvertureRepository caisseOuvertureRepository) {
        this.caisseOuvertureRepository = caisseOuvertureRepository;
    }

    /**
     * POST  /caisse-ouvertures : Create a new caisseOuverture.
     *
     * @param caisseOuverture the caisseOuverture to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caisseOuverture, or with status 400 (Bad Request) if the caisseOuverture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caisse-ouvertures")
    @Timed
    public ResponseEntity<CaisseOuverture> createCaisseOuverture(@Valid @RequestBody CaisseOuverture caisseOuverture) throws Exception {
        log.debug("REST request to save CaisseOuverture : {}", caisseOuverture);
        if (caisseOuverture.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new caisseOuverture cannot already have an ID")).body(null);
        }
        CaisseOuverture result = caisseOuvertureRepository.save(caisseOuverture);
        return ResponseEntity.created(new URI("/api/caisse-ouvertures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /caisse-ouvertures : Updates an existing caisseOuverture.
     *
     * @param caisseOuverture the caisseOuverture to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caisseOuverture,
     * or with status 400 (Bad Request) if the caisseOuverture is not valid,
     * or with status 500 (Internal Server Error) if the caisseOuverture couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/caisse-ouvertures")
    @Timed
    public ResponseEntity<CaisseOuverture> updateCaisseOuverture(@Valid @RequestBody CaisseOuverture caisseOuverture) throws Exception {
        log.debug("REST request to update CaisseOuverture : {}", caisseOuverture);
        if (caisseOuverture.getId() == null) {
            return createCaisseOuverture(caisseOuverture);
        }
        CaisseOuverture result = caisseOuvertureRepository.save(caisseOuverture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caisseOuverture.getId().toString()))
            .body(result);
    }

    /**
     * GET  /caisse-ouvertures : get all the caisseOuvertures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of caisseOuvertures in body
     */
    @GetMapping("/caisse-ouvertures")
    @Timed
    public ResponseEntity<List<CaisseOuverture>> getAllCaisseOuvertures(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CaisseOuvertures");
        Page<CaisseOuverture> page = caisseOuvertureRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caisse-ouvertures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /caisse-ouvertures/:id : get the "id" caisseOuverture.
     *
     * @param id the id of the caisseOuverture to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caisseOuverture, or with status 404 (Not Found)
     */
    @GetMapping("/caisse-ouvertures/{id}")
    @Timed
    public ResponseEntity<CaisseOuverture> getCaisseOuverture(@PathVariable Long id) {
        log.debug("REST request to get CaisseOuverture : {}", id);
        CaisseOuverture caisseOuverture = caisseOuvertureRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caisseOuverture));
    }

    /**
     * DELETE  /caisse-ouvertures/:id : delete the "id" caisseOuverture.
     *
     * @param id the id of the caisseOuverture to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/caisse-ouvertures/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaisseOuverture(@PathVariable Long id) {
        log.debug("REST request to delete CaisseOuverture : {}", id);
        caisseOuvertureRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
