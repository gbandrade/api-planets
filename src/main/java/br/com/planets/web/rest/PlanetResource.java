package br.com.planets.web.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.planets.components.HttpRequest;
import br.com.planets.config.ApplicationProperties;
import br.com.planets.service.PlanetService;
import br.com.planets.service.dto.PlanetCreateDTO;
import br.com.planets.service.dto.PlanetDTO;
import br.com.planets.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link br.com.planets.domain.Planet}.
 */
@RestController
@RequestMapping("/api")
public class PlanetResource {

    private final Logger log = LoggerFactory.getLogger(PlanetResource.class);

    private static final String ENTITY_NAME = "planet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanetService planetService;
    private final HttpRequest httpRequest;
    private final ApplicationProperties prop;
    private final MessageSource messageSource;
    
    public PlanetResource(PlanetService planetService, HttpRequest httpRequest, 
    		ApplicationProperties prop, MessageSource messageSource) {
        this.planetService = planetService;
        this.httpRequest = httpRequest;
        this.prop = prop;
        this.messageSource = messageSource;
    }

    /**
     * {@code POST  /planets} : Create a new planet.
     *
     * @param planetCreatedDTO the planetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planetDTO, or with status {@code 400 (Bad Request)} if the planet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws IOException 
     * @throws ClientProtocolException 
     * @throws JSONException 
     */
    @PostMapping("/planets")
    public ResponseEntity<PlanetDTO> createPlanet(@Valid @RequestBody PlanetCreateDTO planetCreatedDTO) throws URISyntaxException, ClientProtocolException, IOException, JSONException {
    	log.debug("REST request to save Planet : {}", planetCreatedDTO);
    	PlanetDTO planetDTO = new PlanetDTO();
    	
    	if (planetCreatedDTO.getId() != null) {
            throw new BadRequestAlertException("A new planet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        HttpResponse httpResponse = 
        		httpRequest.get(prop.getApi().getPath() + prop.getApi().getEndpoint().getGetPlanets() + 
        				URLEncoder.encode(planetCreatedDTO.getNome(), StandardCharsets.UTF_8));
        JSONArray results = 
        		httpRequest.getResponseObject(httpResponse).getJSONArray("results");
        
        if(results.length() == 0) 	
        	throw new BadRequestAlertException(messageSource.getMessage("planet.invalid.name.message", null, Locale.ENGLISH), 
        			ENTITY_NAME, 
        			"invalid_name");
        else {
        	JSONObject planetInfo = results.getJSONObject(0);
        	BeanUtils.copyProperties(planetCreatedDTO, planetDTO);
        	planetDTO.setAparicoes(((JSONArray) planetInfo.get("films")).length());
        }
        
        try {
        	planetDTO = planetService.save(planetDTO);
        } catch (DataIntegrityViolationException e) {
        	if(e.contains(ConstraintViolationException.class)) {
        		ConstraintViolationException violation = 
						(ConstraintViolationException) e.getCause();
				if(violation.getConstraintName().equals("SYSTEM.UX_PLANET_NOME")) {
					throw new BadRequestAlertException(messageSource.getMessage("planet.name.inuse", null, Locale.ENGLISH), ENTITY_NAME, "name_in_use");
				}
        	}
		}
        
        return ResponseEntity.created(new URI("/api/planets/" + planetCreatedDTO.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, planetDTO.getId().toString()))
                .body(planetDTO);
    }

    /**
     * {@code GET  /planets} : get all the planets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planets in body.
     */
    @GetMapping("/planets")
    public ResponseEntity<List<PlanetDTO>> getAllPlanets(Pageable pageable) {
        log.debug("REST request to get all Planets");
        final Page<PlanetDTO> page = planetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * {@code GET  /planets/:id} : get the "id" planet.
     *
     * @param id the id of the planetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/planets/findByid/{id}")
    public ResponseEntity<PlanetDTO> getPlanet(@PathVariable Long id) {
        log.debug("REST request to get Planet : {}", id);
        Optional<PlanetDTO> planetDTO = planetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(planetDTO);
    }
    
    /**
     * {@code GET  /planets/:name} : get the "name" planet.
     *
     * @param name the name of the planetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/planets/findByname/{name}")
    public ResponseEntity<PlanetDTO> getPlanet(@PathVariable String name) {
        log.debug("REST request to get Planet by name: {}", name);
        Optional<PlanetDTO> planetDTO = planetService.findOneByNome(name);
        return ResponseUtil.wrapOrNotFound(planetDTO);
    }

    /**
     * {@code DELETE  /planets/:id} : delete the "id" planet.
     *
     * @param id the id of the planetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/planets/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable Long id) {
        log.debug("REST request to delete Planet : {}", id);
        planetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}