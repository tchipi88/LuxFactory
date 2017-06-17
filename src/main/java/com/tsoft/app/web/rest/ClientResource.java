package com.tsoft.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.tsoft.app.domain.Client;

import com.tsoft.app.repository.ClientRepository;
import com.tsoft.app.repository.search.ClientSearchRepository;
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
 * REST controller for managing Client.
 */
@RestController
@RequestMapping("/api")
public class ClientResource {

    private final Logger log = LoggerFactory.getLogger(ClientResource.class);

    private static final String ENTITY_NAME = "client";
        
    private final ClientRepository clientRepository;

    private final ClientSearchRepository clientSearchRepository;

    public ClientResource(ClientRepository clientRepository, ClientSearchRepository clientSearchRepository) {
        this.clientRepository = clientRepository;
        this.clientSearchRepository = clientSearchRepository;
    }

    /**
     * POST  /clients : Create a new client.
     *
     * @param client the client to create
     * @return the ResponseEntity with status 201 (Created) and with body the new client, or with status 400 (Bad Request) if the client has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clients")
    @Timed
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to save Client : {}", client);
        if (client.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new client cannot already have an ID")).body(null);
        }
        Client result = clientRepository.save(client);
        clientSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clients : Updates an existing client.
     *
     * @param client the client to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated client,
     * or with status 400 (Bad Request) if the client is not valid,
     * or with status 500 (Internal Server Error) if the client couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clients")
    @Timed
    public ResponseEntity<Client> updateClient(@Valid @RequestBody Client client) throws URISyntaxException {
        log.debug("REST request to update Client : {}", client);
        if (client.getId() == null) {
            return createClient(client);
        }
        Client result = clientRepository.save(client);
        clientSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, client.getId().toString()))
            .body(result);
    }

   /**
     * GET  /clients : get all the clients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clients in body
     */
    @GetMapping("/clients")
    @Timed
    public ResponseEntity<List<Client>> getAllClients(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Clients");
        Page<Client> page = clientRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clients/:id : get the "id" client.
     *
     * @param id the id of the client to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client, or with status 404 (Not Found)
     */
    @GetMapping("/clients/{id}")
    @Timed
    public ResponseEntity<Client> getClient(@PathVariable Long id) {
        log.debug("REST request to get Client : {}", id);
        Client client = clientRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(client));
    }

    /**
     * DELETE  /clients/:id : delete the "id" client.
     *
     * @param id the id of the client to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clients/{id}")
    @Timed
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.debug("REST request to delete Client : {}", id);
        clientRepository.delete(id);
        clientSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/clients?query=:query : search for the client corresponding
     * to the query.
     *
     * @param query the query of the client search 
     * @return the result of the search
     */
    @GetMapping("/_search/clients")
    @Timed
    public ResponseEntity<List<Client>> searchClients(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search Clients for query {}", query);
      Page<Client> page = clientSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




}
