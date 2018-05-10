package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Site;

import com.tsoft.app.repository.SiteRepository;
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
 * REST controller for managing Site.
 */
@RestController
@RequestMapping("/api")
public class SiteResource {

    private final Logger log = LoggerFactory.getLogger(SiteResource.class);

    private static final String ENTITY_NAME = "site";
        
    private final SiteRepository siteRepository;


    public SiteResource(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    /**
     * POST  /sites : Create a new site.
     *
     * @param site the site to create
     * @return the ResponseEntity with status 201 (Created) and with body the new site, or with status 400 (Bad Request) if the site has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sites")
    @Timed
    public ResponseEntity<Site> createSite(@Valid @RequestBody Site site) throws Exception {
        log.debug("REST request to save Site : {}", site);
        if (site.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new site cannot already have an ID")).body(null);
        }
        Site result = siteRepository.save(site);
        return ResponseEntity.created(new URI("/api/sites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sites : Updates an existing site.
     *
     * @param site the site to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated site,
     * or with status 400 (Bad Request) if the site is not valid,
     * or with status 500 (Internal Server Error) if the site couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sites")
    @Timed
    public ResponseEntity<Site> updateSite(@Valid @RequestBody Site site) throws Exception {
        log.debug("REST request to update Site : {}", site);
        if (site.getId() == null) {
            return createSite(site);
        }
        Site result = siteRepository.save(site);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, site.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sites : get all the sites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sites in body
     */
    @GetMapping("/sites")
    @Timed
    public ResponseEntity<List<Site>> getAllSites(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Sites");
        Page<Site> page = siteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sites");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /sites/:id : get the "id" site.
     *
     * @param id the id of the site to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the site, or with status 404 (Not Found)
     */
    @GetMapping("/sites/{id}")
    @Timed
    public ResponseEntity<Site> getSite(@PathVariable Long id) {
        log.debug("REST request to get Site : {}", id);
        Site site = siteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(site));
    }

    /**
     * DELETE  /sites/:id : delete the "id" site.
     *
     * @param id the id of the site to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sites/{id}")
    @Timed
    public ResponseEntity<Void> deleteSite(@PathVariable Long id) {
        log.debug("REST request to delete Site : {}", id);
        siteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
