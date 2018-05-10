package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Zone;

import com.tsoft.app.repository.ZoneRepository;
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
 * REST controller for managing Zone.
 */
@RestController
@RequestMapping("/api")
public class ZoneResource {

    private final Logger log = LoggerFactory.getLogger(ZoneResource.class);

    private static final String ENTITY_NAME = "zone";
        
    private final ZoneRepository zoneRepository;


    public ZoneResource(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    /**
     * POST  /zones : Create a new zone.
     *
     * @param zone the zone to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zone, or with status 400 (Bad Request) if the zone has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zones")
    @Timed
    public ResponseEntity<Zone> createZone(@Valid @RequestBody Zone zone) throws Exception {
        log.debug("REST request to save Zone : {}", zone);
        if (zone.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new zone cannot already have an ID")).body(null);
        }
        Zone result = zoneRepository.save(zone);
        return ResponseEntity.created(new URI("/api/zones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zones : Updates an existing zone.
     *
     * @param zone the zone to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zone,
     * or with status 400 (Bad Request) if the zone is not valid,
     * or with status 500 (Internal Server Error) if the zone couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zones")
    @Timed
    public ResponseEntity<Zone> updateZone(@Valid @RequestBody Zone zone) throws Exception {
        log.debug("REST request to update Zone : {}", zone);
        if (zone.getId() == null) {
            return createZone(zone);
        }
        Zone result = zoneRepository.save(zone);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zone.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zones : get all the zones.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zones in body
     */
    @GetMapping("/zones")
    @Timed
    public ResponseEntity<List<Zone>> getAllZones(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Zones");
        Page<Zone> page = zoneRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zones");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /zones/:id : get the "id" zone.
     *
     * @param id the id of the zone to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zone, or with status 404 (Not Found)
     */
    @GetMapping("/zones/{id}")
    @Timed
    public ResponseEntity<Zone> getZone(@PathVariable Long id) {
        log.debug("REST request to get Zone : {}", id);
        Zone zone = zoneRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zone));
    }

    /**
     * DELETE  /zones/:id : delete the "id" zone.
     *
     * @param id the id of the zone to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zones/{id}")
    @Timed
    public ResponseEntity<Void> deleteZone(@PathVariable Long id) {
        log.debug("REST request to delete Zone : {}", id);
        zoneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
