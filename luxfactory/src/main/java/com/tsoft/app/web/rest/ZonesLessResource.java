package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.ZonesLess;

import com.tsoft.app.repository.ZonesLessRepository;
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
 * REST controller for managing ZonesLess.
 */
@RestController
@RequestMapping("/api")
public class ZonesLessResource {

    private final Logger log = LoggerFactory.getLogger(ZonesLessResource.class);

    private static final String ENTITY_NAME = "zonesLess";
        
    private final ZonesLessRepository zonesLessRepository;


    public ZonesLessResource(ZonesLessRepository zonesLessRepository) {
        this.zonesLessRepository = zonesLessRepository;
    }

    /**
     * POST  /zones-lesss : Create a new zonesLess.
     *
     * @param zonesLess the zonesLess to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zonesLess, or with status 400 (Bad Request) if the zonesLess has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zones-lesss")
    @Timed
    public ResponseEntity<ZonesLess> createZonesLess(@Valid @RequestBody ZonesLess zonesLess) throws Exception {
        log.debug("REST request to save ZonesLess : {}", zonesLess);
        if (zonesLess.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new zonesLess cannot already have an ID")).body(null);
        }
        ZonesLess result = zonesLessRepository.save(zonesLess);
        return ResponseEntity.created(new URI("/api/zones-lesss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zones-lesss : Updates an existing zonesLess.
     *
     * @param zonesLess the zonesLess to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zonesLess,
     * or with status 400 (Bad Request) if the zonesLess is not valid,
     * or with status 500 (Internal Server Error) if the zonesLess couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zones-lesss")
    @Timed
    public ResponseEntity<ZonesLess> updateZonesLess(@Valid @RequestBody ZonesLess zonesLess) throws Exception {
        log.debug("REST request to update ZonesLess : {}", zonesLess);
        if (zonesLess.getId() == null) {
            return createZonesLess(zonesLess);
        }
        ZonesLess result = zonesLessRepository.save(zonesLess);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zonesLess.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zones-lesss : get all the zonesLesss.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zonesLesss in body
     */
    @GetMapping("/zones-lesss")
    @Timed
    public ResponseEntity<List<ZonesLess>> getAllZonesLesss(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ZonesLesss");
        Page<ZonesLess> page = zonesLessRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zones-lesss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /zones-lesss/:id : get the "id" zonesLess.
     *
     * @param id the id of the zonesLess to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zonesLess, or with status 404 (Not Found)
     */
    @GetMapping("/zones-lesss/{id}")
    @Timed
    public ResponseEntity<ZonesLess> getZonesLess(@PathVariable Long id) {
        log.debug("REST request to get ZonesLess : {}", id);
        ZonesLess zonesLess = zonesLessRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zonesLess));
    }

    /**
     * DELETE  /zones-lesss/:id : delete the "id" zonesLess.
     *
     * @param id the id of the zonesLess to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zones-lesss/{id}")
    @Timed
    public ResponseEntity<Void> deleteZonesLess(@PathVariable Long id) {
        log.debug("REST request to delete ZonesLess : {}", id);
        zonesLessRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
