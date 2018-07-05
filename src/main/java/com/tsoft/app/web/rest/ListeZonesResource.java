package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.ListeZones;
import com.tsoft.app.domain.enumeration.TypeCommande;
import com.tsoft.app.repository.ListeZonesRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing ListeZones.
 */
@RestController
@RequestMapping("/api")
public class ListeZonesResource {

    private final Logger log = LoggerFactory.getLogger(ListeZonesResource.class);

    private static final String ENTITY_NAME = "listeZones";
        
    private final ListeZonesRepository listeZonesRepository;


    public ListeZonesResource(ListeZonesRepository listeZonesRepository) {
        this.listeZonesRepository = listeZonesRepository;
    }

    /**
     * POST  /liste-zoness : Create a new listeZones.
     *
     * @param listeZones the listeZones to create
     * @return the ResponseEntity with status 201 (Created) and with body the new listeZones, or with status 400 (Bad Request) if the listeZones has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/liste-zoness")
    @Timed
    public ResponseEntity<ListeZones> createListeZones(@Valid @RequestBody ListeZones listeZones) throws Exception {
        log.debug("REST request to save ListeZones : {}", listeZones);
        if (listeZones.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new listeZones cannot already have an ID")).body(null);
        }
        ListeZones result = listeZonesRepository.save(listeZones);
        return ResponseEntity.created(new URI("/api/liste-zoness/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /liste-zoness : Updates an existing listeZones.
     *
     * @param listeZones the listeZones to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated listeZones,
     * or with status 400 (Bad Request) if the listeZones is not valid,
     * or with status 500 (Internal Server Error) if the listeZones couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/liste-zoness")
    @Timed
    public ResponseEntity<ListeZones> updateListeZones(@Valid @RequestBody ListeZones listeZones) throws Exception {
        log.debug("REST request to update ListeZones : {}", listeZones);
        if (listeZones.getId() == null) {
            return createListeZones(listeZones);
        }
        ListeZones result = listeZonesRepository.save(listeZones);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, listeZones.getId().toString()))
            .body(result);
    }

    /**
     * GET  /liste-zoness : get all the listeZoness.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of listeZoness in body
     */
    @GetMapping("/liste-zoness")
    @Timed
    public ResponseEntity<List<ListeZones>> getAllListeZoness(@ApiParam Pageable pageable) {
        log.debug("REST request to get all ListeZoness");
        Page<ListeZones> page = listeZonesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/liste-zoness");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /liste-zoness/:id : get the "id" listeZones.
     *
     * @param id the id of the listeZones to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the listeZones, or with status 404 (Not Found)
     */
    @GetMapping("/liste-zoness/{id}")
    @Timed
    public ResponseEntity<ListeZones> getListeZones(@PathVariable Long id) {
        log.debug("REST request to get ListeZones : {}", id);
        ListeZones listeZones = listeZonesRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(listeZones));
    }

    /**
     * DELETE  /liste-zoness/:id : delete the "id" listeZones.
     *
     * @param id the id of the listeZones to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/liste-zoness/{id}")
    @Timed
    public ResponseEntity<Void> deleteListeZones(@PathVariable Long id) {
        log.debug("REST request to delete ListeZones : {}", id);
        listeZonesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

	
	/**
	 * GET /liste-zoness : get a page of listeZones between the activite and
	 * zone and responsable.
	 *
	 * @param activite the activity of the zone to get
	 * @param entrepot the zone to get
	 * @param responsable the responsable of the zone to get
	 * @param pageable the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of listeZones
	 * in body
	 */
	@GetMapping(path = "/liste-zoness", params = {"activite", "entrepot","responsable"})
	@Timed
	public ResponseEntity<List<ListeZones>> searchListeZones(
	        @RequestParam(value = "activite") String activite,
	        @RequestParam(value = "entrepot") String entrepot,
	        @RequestParam(value = "responsable") String responsable, 
	        @ApiParam Pageable pageable, @ApiParam TypeCommande type) {
	    log.debug("REST request to search for a page of ListeZones for  {}  and {} and {}", activite, entrepot,responsable);
	    Page<ListeZones> page = listeZonesRepository.findAllByActiviteAndEntrepotAndResponsableContaining(activite, entrepot, responsable, pageable);
	    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/liste-zoness");
	    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}


}
