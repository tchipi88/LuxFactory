package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.ZoneProduit;

import com.tsoft.app.repository.ZoneProduitRepository;
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
 * REST controller for managing ZoneProduit.
 */
@RestController
@RequestMapping("/api")
public class ZoneProduitResource {

    private final Logger log = LoggerFactory.getLogger(ZoneProduitResource.class);

    private static final String ENTITY_NAME = "zoneProduit";
        
    private final ZoneProduitRepository zoneProduitRepository;


    public ZoneProduitResource(ZoneProduitRepository zoneProduitRepository) {
        this.zoneProduitRepository = zoneProduitRepository;
    }

    /**
     * POST  /zone-produits : Create a new zoneProduit.
     *
     * @param zoneProduit the zoneProduit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zoneProduit, or with status 400 (Bad Request) if the zoneProduit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zone-produits")
    @Timed
    public ResponseEntity<ZoneProduit> createZoneProduit(@Valid @RequestBody ZoneProduit zoneProduit) throws Exception {
        log.debug("REST request to save ZoneProduit : {}", zoneProduit);
        if (zoneProduit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new zoneProduit cannot already have an ID")).body(null);
        }
        ZoneProduit result = zoneProduitRepository.save(zoneProduit);
        return ResponseEntity.created(new URI("/api/zone-produits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zone-produits : Updates an existing zoneProduit.
     *
     * @param zoneProduit the zoneProduit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zoneProduit,
     * or with status 400 (Bad Request) if the zoneProduit is not valid,
     * or with status 500 (Internal Server Error) if the zoneProduit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zone-produits")
    @Timed
    public ResponseEntity<ZoneProduit> updateZoneProduit(@Valid @RequestBody ZoneProduit zoneProduit) throws Exception {
        log.debug("REST request to update ZoneProduit : {}", zoneProduit);
        if (zoneProduit.getId() == null) {
            return createZoneProduit(zoneProduit);
        }
        ZoneProduit result = zoneProduitRepository.save(zoneProduit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zoneProduit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zone-produits : get all the zoneProduits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zoneProduits in body
     */
    @GetMapping("/zone-produits")
    @Timed
    public ResponseEntity<List<ZoneProduit>> getAllZoneProduits(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ZoneProduits");
        Page<ZoneProduit> page = zoneProduitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/zone-produits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /zone-produits/:id : get the "id" zoneProduit.
     *
     * @param id the id of the zoneProduit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zoneProduit, or with status 404 (Not Found)
     */
    @GetMapping("/zone-produits/{id}")
    @Timed
    public ResponseEntity<ZoneProduit> getZoneProduit(@PathVariable Long id) {
        log.debug("REST request to get ZoneProduit : {}", id);
        ZoneProduit zoneProduit = zoneProduitRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zoneProduit));
    }

    /**
     * DELETE  /zone-produits/:id : delete the "id" zoneProduit.
     *
     * @param id the id of the zoneProduit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zone-produits/{id}")
    @Timed
    public ResponseEntity<Void> deleteZoneProduit(@PathVariable Long id) {
        log.debug("REST request to delete ZoneProduit : {}", id);
        zoneProduitRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
