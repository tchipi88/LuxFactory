package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.EcritureCompteAnalytique;

import com.tsoft.app.repository.EcritureCompteAnalytiqueRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing EcritureCompteAnalytique.
 */
@RestController
@RequestMapping("/api")
public class EcritureCompteAnalytiqueResource {

    private final Logger log = LoggerFactory.getLogger(EcritureCompteAnalytiqueResource.class);

    private static final String ENTITY_NAME = "ecritureCompteAnalytique";
        
    private final EcritureCompteAnalytiqueRepository ecritureCompteAnalytiqueRepository;


    public EcritureCompteAnalytiqueResource(EcritureCompteAnalytiqueRepository ecritureCompteAnalytiqueRepository) {
        this.ecritureCompteAnalytiqueRepository = ecritureCompteAnalytiqueRepository;
    }

    /**
     * POST  /ecriture-compte-analytiques : Create a new ecritureCompteAnalytique.
     *
     * @param ecritureCompteAnalytique the ecritureCompteAnalytique to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ecritureCompteAnalytique, or with status 400 (Bad Request) if the ecritureCompteAnalytique has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ecriture-compte-analytiques")
    @Timed
    public ResponseEntity<EcritureCompteAnalytique> createEcritureCompteAnalytique(@Valid @RequestBody EcritureCompteAnalytique ecritureCompteAnalytique) throws Exception {
        log.debug("REST request to save EcritureCompteAnalytique : {}", ecritureCompteAnalytique);
        if (ecritureCompteAnalytique.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ecritureCompteAnalytique cannot already have an ID")).body(null);
        }
        EcritureCompteAnalytique result = ecritureCompteAnalytiqueRepository.save(ecritureCompteAnalytique);
        return ResponseEntity.created(new URI("/api/ecriture-compte-analytiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ecriture-compte-analytiques : Updates an existing ecritureCompteAnalytique.
     *
     * @param ecritureCompteAnalytique the ecritureCompteAnalytique to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ecritureCompteAnalytique,
     * or with status 400 (Bad Request) if the ecritureCompteAnalytique is not valid,
     * or with status 500 (Internal Server Error) if the ecritureCompteAnalytique couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ecriture-compte-analytiques")
    @Timed
    public ResponseEntity<EcritureCompteAnalytique> updateEcritureCompteAnalytique(@Valid @RequestBody EcritureCompteAnalytique ecritureCompteAnalytique) throws Exception {
        log.debug("REST request to update EcritureCompteAnalytique : {}", ecritureCompteAnalytique);
        if (ecritureCompteAnalytique.getId() == null) {
            return createEcritureCompteAnalytique(ecritureCompteAnalytique);
        }
        EcritureCompteAnalytique result = ecritureCompteAnalytiqueRepository.save(ecritureCompteAnalytique);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ecritureCompteAnalytique.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ecriture-compte-analytiques : get all the ecritureCompteAnalytiques.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ecritureCompteAnalytiques in body
     */
    @GetMapping("/ecriture-compte-analytiques")
    @Timed
    public ResponseEntity<List<EcritureCompteAnalytique>> getAllEcritureCompteAnalytiques(@ApiParam Pageable pageable) {
        log.debug("REST request to get all EcritureCompteAnalytiques");
        Page<EcritureCompteAnalytique> page = ecritureCompteAnalytiqueRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ecriture-compte-analytiques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /ecriture-compte-analytiques/:id : get the "id" ecritureCompteAnalytique.
     *
     * @param id the id of the ecritureCompteAnalytique to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ecritureCompteAnalytique, or with status 404 (Not Found)
     */
    @GetMapping("/ecriture-compte-analytiques/{id}")
    @Timed
    public ResponseEntity<EcritureCompteAnalytique> getEcritureCompteAnalytique(@PathVariable Long id) {
        log.debug("REST request to get EcritureCompteAnalytique : {}", id);
        EcritureCompteAnalytique ecritureCompteAnalytique = ecritureCompteAnalytiqueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ecritureCompteAnalytique));
    }

    /**
     * DELETE  /ecriture-compte-analytiques/:id : delete the "id" ecritureCompteAnalytique.
     *
     * @param id the id of the ecritureCompteAnalytique to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ecriture-compte-analytiques/{id}")
    @Timed
    public ResponseEntity<Void> deleteEcritureCompteAnalytique(@PathVariable Long id) {
        log.debug("REST request to delete EcritureCompteAnalytique : {}", id);
        ecritureCompteAnalytiqueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

     /**
     * GET /ecriture-compte-analytiques : get a page of EcritureCompteAnalytiques between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of ecriture-compte-analytiques to get
     * @param toDate the end of the time period of ecriture-compte-analytiques to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * EcritureCompteAnalytiques in body
     */
    @GetMapping(path = "/ecriture-compte-analytiques", params = {"fromDate", "toDate"})
    public ResponseEntity<List<EcritureCompteAnalytique>> getByDates(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable) {

        Page<EcritureCompteAnalytique> page = ecritureCompteAnalytiqueRepository.findAllByDateEcritureBetween(fromDate.atTime(0, 0), toDate.atTime(23, 59), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ecriture-compte-analytiques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
