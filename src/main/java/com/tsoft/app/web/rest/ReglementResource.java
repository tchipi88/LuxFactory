package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Reglement;

import com.tsoft.app.repository.ReglementRepository;
import com.tsoft.app.service.ReglementService;
import com.tsoft.app.web.rest.util.HeaderUtil;
import com.tsoft.app.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Reglement.
 */
@RestController
@RequestMapping("/api")
public class ReglementResource {

    private final Logger log = LoggerFactory.getLogger(ReglementResource.class);

    private static final String ENTITY_NAME = "reglement";

    private final ReglementRepository reglementRepository;

    private final ReglementService reglementService;

    public ReglementResource(ReglementRepository reglementRepository, ReglementService reglementService) {
        this.reglementRepository = reglementRepository;
        this.reglementService = reglementService;
    }

    /**
     * POST /reglements : Create a new reglement.
     *
     * @param reglement the reglement to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new reglement, or with status 400 (Bad Request) if the reglement has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/reglements")
    @Timed
    public ResponseEntity<Reglement> createReglement(@Valid @RequestBody Reglement reglement) throws Exception {
        log.debug("REST request to save Reglement : {}", reglement);
        if (reglement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new reglement cannot already have an ID")).body(null);
        }
        Reglement result = reglementService.save(reglement);
        return ResponseEntity.created(new URI("/api/reglements/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /reglements : Updates an existing reglement.
     *
     * @param reglement the reglement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * reglement, or with status 400 (Bad Request) if the reglement is not
     * valid, or with status 500 (Internal Server Error) if the reglement
     * couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/reglements")
    @Timed
    public ResponseEntity<Reglement> updateReglement(@Valid @RequestBody Reglement reglement) throws Exception {
        log.debug("REST request to update Reglement : {}", reglement);
        if (reglement.getId() == null) {
            return createReglement(reglement);
        }
        Reglement result = reglementService.save(reglement);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, reglement.getId().toString()))
                .body(result);
    }

    /**
     * GET /reglements : get all the reglements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     * reglements in body
     */
    @GetMapping("/reglements")
    @Timed
    public ResponseEntity<List<Reglement>> getAllReglements(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Reglements");
        Page<Reglement> page = reglementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/reglements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /reglements/:id : get the "id" reglement.
     *
     * @param id the id of the reglement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * reglement, or with status 404 (Not Found)
     */
    @GetMapping("/reglements/{id}")
    @Timed
    public ResponseEntity<Reglement> getReglement(@PathVariable Long id) {
        log.debug("REST request to get Reglement : {}", id);
        Reglement reglement = reglementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(reglement));
    }

    /**
     * DELETE /reglements/:id : delete the "id" reglement.
     *
     * @param id the id of the reglement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/reglements/{id}")
    @Timed
    public ResponseEntity<Void> deleteReglement(@PathVariable Long id) {
        log.debug("REST request to delete Reglement : {}", id);
        reglementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET /reglementss/:id : get the "id" commande.
     *
     * @param id the id of the commande
     * @return la liste des reglements associés à la commande passé en arguments
     * with status 200 (OK) and with body the commandeLigne, or with status 404
     * (Not Found)
     */
    @GetMapping("/reglementss/{id}")
    @Timed
    public List<Reglement> getCommandeLigneByCommande(@PathVariable Long id) {
        log.debug("REST request to get Reglements to Commande : {}", id);
        return reglementRepository.findByCommandeId(id);
    }

    /**
     * GET /print-reglement/:id : print ticket for the "id" reglement.
     *
     * @param id the id of the reglement to print
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/print-reglement/{id}")
    @Timed
    public ResponseEntity<byte[]> printReglement(@PathVariable Long id) throws Exception {
        log.debug("REST request to print ticket Reglement : {}", id);
        Resource resource = reglementService.print(reglementRepository.findOne(id));
        InputStream in = resource.getInputStream();
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }


}
