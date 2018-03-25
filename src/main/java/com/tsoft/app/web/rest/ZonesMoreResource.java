package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.ZonesMore;

import com.tsoft.app.repository.ZonesMoreRepository;
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
 * REST controller for managing ZonesMore.
 */
@RestController
@RequestMapping("/api")
public class ZonesMoreResource {

    private final Logger log = LoggerFactory.getLogger(ZonesMoreResource.class);

    private static final String ENTITY_NAME = "zonesMore";
        
    private final ZonesMoreRepository zonesMoreRepository;


    public ZonesMoreResource(ZonesMoreRepository zonesMoreRepository) {
        this.zonesMoreRepository = zonesMoreRepository;
    }

    /**
     * POST  /zones-mores : Create a new zonesMore.
     *
     * @param zonesMore the zonesMore to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zonesMore, or with status 400 (Bad Request) if the zonesMore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zones-mores")
    @Timed
    public ResponseEntity<ZonesMore> createZonesMore(@Valid @RequestBody ZonesMore zonesMore) throws Exception {
        log.debug("REST request to save ZonesMore : {}", zonesMore);
        if (zonesMore.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new zonesMore cannot already have an ID")).body(null);
        }
        ZonesMore result = zonesMoreRepository.save(zonesMore);
        return ResponseEntity.created(new URI("/api/zones-mores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zones-mores : Updates an existing zonesMore.
     *
     * @param zonesMore the zonesMore to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zonesMore,
     * or with status 400 (Bad Request) if the zonesMore is not valid,
     * or with status 500 (Internal Server Error) if the zonesMore couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zones-mores")
    @Timed
    public ResponseEntity<ZonesMore> updateZonesMore(@Valid @RequestBody ZonesMore zonesMore) throws Exception {
        log.debug("REST request to update ZonesMore : {}", zonesMore);
        if (zonesMore.getId() == null) {
            return createZonesMore(zonesMore);
        }
        ZonesMore result = zonesMoreRepository.save(zonesMore);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zonesMore.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zones-mores : get all the zonesMores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zonesMores in body
     */
    @GetMapping("/zones-mores")
    @Timed
    public ResponseEntity<List<ZonesMore>> getAllZonesMores(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ZonesMores");
        Page<ZonesMore> page = zonesMoreRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zones-mores");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /zones-mores/:id : get the "id" zonesMore.
     *
     * @param id the id of the zonesMore to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zonesMore, or with status 404 (Not Found)
     */
    @GetMapping("/zones-mores/{id}")
    @Timed
    public ResponseEntity<ZonesMore> getZonesMore(@PathVariable Long id) {
        log.debug("REST request to get ZonesMore : {}", id);
        ZonesMore zonesMore = zonesMoreRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zonesMore));
    }

    /**
     * DELETE  /zones-mores/:id : delete the "id" zonesMore.
     *
     * @param id the id of the zonesMore to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zones-mores/{id}")
    @Timed
    public ResponseEntity<Void> deleteZonesMore(@PathVariable Long id) {
        log.debug("REST request to delete ZonesMore : {}", id);
        zonesMoreRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
