package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Encaissement;

import com.tsoft.app.repository.EncaissementRepository;
import com.tsoft.app.service.EncaissementService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Encaissement.
 */
@RestController
@RequestMapping("/api")
public class EncaissementResource {

   private final Logger log = LoggerFactory.getLogger(EncaissementResource.class);

    private static final String ENTITY_NAME = "encaissement";

    private final EncaissementRepository encaissementRepository;

    private final EncaissementService encaissementService;

    public EncaissementResource(EncaissementRepository encaissementRepository,EncaissementService encaissementService) {
        this.encaissementRepository = encaissementRepository;
        this.encaissementService=encaissementService;
    }

    /**
     * POST /encaissements : Create a new encaissement.
     *
     * @param encaissement the encaissement to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new encaissement, or with status 400 (Bad Request) if the encaissement
     * has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/encaissements")
    @Timed
    public ResponseEntity<Encaissement> createEncaissement(@Valid @RequestBody Encaissement encaissement) throws Exception {
        log.debug("REST request to save Encaissement : {}", encaissement);
        if (encaissement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new encaissement cannot already have an ID")).body(null);
        }
        Encaissement result = encaissementService.save(encaissement);
        return ResponseEntity.created(new URI("/api/encaissements/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /encaissements : Updates an existing encaissement.
     *
     * @param encaissement the encaissement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * encaissement, or with status 400 (Bad Request) if the encaissement is not
     * valid, or with status 500 (Internal Server Error) if the encaissement
     * couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/encaissements")
    @Timed
    public ResponseEntity<Encaissement> updateEncaissement(@Valid @RequestBody Encaissement encaissement) throws Exception {
        log.debug("REST request to update Encaissement : {}", encaissement);
        if (encaissement.getId() == null) {
            return createEncaissement(encaissement);
        }
        Encaissement result = encaissementService.save(encaissement);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, encaissement.getId().toString()))
                .body(result);
    }

    /**
     * GET /encaissements : get all the encaissements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * encaissements in body
     */
    @GetMapping("/encaissements")
    @Timed
    public ResponseEntity<List<Encaissement>> getAllEncaissements(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Encaissements");
        Page<Encaissement> page = encaissementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/encaissements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /encaissements/:id : get the "id" encaissement.
     *
     * @param id the id of the encaissement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * encaissement, or with status 404 (Not Found)
     */
    @GetMapping("/encaissements/{id}")
    @Timed
    public ResponseEntity<Encaissement> getEncaissement(@PathVariable Long id) {
        log.debug("REST request to get Encaissement : {}", id);
        Encaissement encaissement = encaissementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(encaissement));
    }

    /**
     * DELETE /encaissements/:id : delete the "id" encaissement.
     *
     * @param id the id of the encaissement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/encaissements/{id}")
    @Timed
    public ResponseEntity<Void> deleteEncaissement(@PathVariable Long id) {
        log.debug("REST request to delete Encaissement : {}", id);
        encaissementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    
    /**
     * GET /encaissements : get a page of encaissements between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of encaissements to get
     * @param toDate the end of the time period of encaissements to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * encaissements in body
     */
    @GetMapping(path = "/encaissements", params = {"fromDate", "toDate"})
    public ResponseEntity<List<Encaissement>> getByDates(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable) {

        Page<Encaissement> page = encaissementRepository.findAllByDateVersementBetween(fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/encaissements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
