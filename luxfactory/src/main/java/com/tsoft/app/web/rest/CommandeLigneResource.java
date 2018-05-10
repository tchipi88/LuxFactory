package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.CommandeLigne;

import com.tsoft.app.repository.CommandeLigneRepository;
import com.tsoft.app.service.CommandeLigneService;
import com.tsoft.app.web.rest.util.HeaderUtil;
import com.tsoft.app.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing CommandeLigne.
 */
@RestController
@RequestMapping("/api")
public class CommandeLigneResource {

   private final Logger log = LoggerFactory.getLogger(CommandeLigneResource.class);

    private static final String ENTITY_NAME = "commandeLigne";
        
    private final CommandeLigneRepository commandeLigneRepository;
    
    private final CommandeLigneService  commandeLigneService;


    public CommandeLigneResource(CommandeLigneRepository commandeLigneRepository, CommandeLigneService commandeLigneService) {
        this.commandeLigneRepository = commandeLigneRepository;
        this.commandeLigneService = commandeLigneService;
    }

    /**
     * POST  /commande-lignes : Create a new commandeLigne.
     *
     * @param commandeLigne the commandeLigne to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commandeLigne, or with status 400 (Bad Request) if the commandeLigne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commande-lignes")
    @Timed
    public ResponseEntity<CommandeLigne> createCommandeLigne( @RequestBody CommandeLigne commandeLigne) throws Exception {
        log.debug("REST request to save CommandeLigne : {}", commandeLigne);
        if (commandeLigne.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commandeLigne cannot already have an ID")).body(null);
        }
        CommandeLigne result =  commandeLigneService.create(commandeLigne);
        return ResponseEntity.created(new URI("/api/commande-lignes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commande-lignes : Updates an existing commandeLigne.
     *
     * @param commandeLigne the commandeLigne to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commandeLigne,
     * or with status 400 (Bad Request) if the commandeLigne is not valid,
     * or with status 500 (Internal Server Error) if the commandeLigne couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commande-lignes")
    @Timed
    public ResponseEntity<CommandeLigne> updateCommandeLigne( @RequestBody CommandeLigne commandeLigne) throws Exception {
        log.debug("REST request to update CommandeLigne : {}", commandeLigne);
        if (commandeLigne.getId() == null) {
            return createCommandeLigne(commandeLigne);
        }
        CommandeLigne result = commandeLigneService.update(commandeLigne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commandeLigne.getId().toString()))
            .body(result);
    }

   /**
     * GET  /commande-lignes : get all the commandeLignes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commandeLignes in body
     */
    @GetMapping("/commande-lignes")
    @Timed
    public ResponseEntity<List<CommandeLigne>> getAllCommandeLignes(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CommandeLignes");
        Page<CommandeLigne> page = commandeLigneRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commande-lignes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commande-lignes/:id : get the "id" commandeLigne.
     *
     * @param id the id of the commandeLigne to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commandeLigne, or with status 404 (Not Found)
     */
    @GetMapping("/commande-lignes/{id}")
    @Timed
    public ResponseEntity<CommandeLigne> getCommandeLigne(@PathVariable Long id) {
        log.debug("REST request to get CommandeLigne : {}", id);
        CommandeLigne commandeLigne = commandeLigneRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commandeLigne));
    }

    /**
     * DELETE  /commande-lignes/:id : delete the "id" commandeLigne.
     *
     * @param id the id of the commandeLigne to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commande-lignes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommandeLigne(@PathVariable Long id)  throws Exception{
        log.debug("REST request to delete CommandeLigne : {}", id);
        commandeLigneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    
    /**
     * GET  /commande-lignes/:id : get the "id" commandeLigne.
     *
     * @param id the id of the commande
     * @return la liste des details commandes associés à la commande passé en arguments with status 200 (OK) and with body the commandeLigne, or with status 404 (Not Found)
     */
    @GetMapping("/commande-ligness/{id}")
    @Timed
    public List<CommandeLigne> getCommandeLigneByCommande(@PathVariable Long id) {
        log.debug("REST request to get CommandeLigne to Commande : {}", id);
        return  commandeLigneRepository.findByCommandeId(id);
    }
   


}
