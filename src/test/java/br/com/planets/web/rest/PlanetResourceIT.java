package br.com.planets.web.rest;

import br.com.planets.ApiPlanetsApp;
import br.com.planets.domain.Planet;
import br.com.planets.repository.PlanetRepository;
import br.com.planets.service.PlanetService;
import br.com.planets.service.dto.PlanetDTO;
import br.com.planets.service.mapper.PlanetMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlanetResource} REST controller.
 */
@SpringBootTest(classes = ApiPlanetsApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PlanetResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIMA = "AAAAAAAAAA";
    private static final String UPDATED_CLIMA = "BBBBBBBBBB";

    private static final String DEFAULT_TERRENO = "AAAAAAAAAA";
    private static final String UPDATED_TERRENO = "BBBBBBBBBB";

    private static final Integer DEFAULT_APARICOES = 1;
    private static final Integer UPDATED_APARICOES = 2;

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private PlanetMapper planetMapper;

    @Autowired
    private PlanetService planetService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanetMockMvc;

    private Planet planet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planet createEntity(EntityManager em) {
        Planet planet = new Planet()
            .nome(DEFAULT_NOME)
            .clima(DEFAULT_CLIMA)
            .terreno(DEFAULT_TERRENO)
            .aparicoes(DEFAULT_APARICOES);
        return planet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Planet createUpdatedEntity(EntityManager em) {
        Planet planet = new Planet()
            .nome(UPDATED_NOME)
            .clima(UPDATED_CLIMA)
            .terreno(UPDATED_TERRENO)
            .aparicoes(UPDATED_APARICOES);
        return planet;
    }

    @BeforeEach
    public void initTest() {
        planet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlanetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = planetRepository.findAll().size();

        // Create the Planet with an existing ID
        planet.setId(1L);
        PlanetDTO planetDTO = planetMapper.toDto(planet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Planet in the database
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setNome(null);

        // Create the Planet, which fails.
        PlanetDTO planetDTO = planetMapper.toDto(planet);


        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClimaIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setClima(null);

        // Create the Planet, which fails.
        PlanetDTO planetDTO = planetMapper.toDto(planet);


        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTerrenoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setTerreno(null);

        // Create the Planet, which fails.
        PlanetDTO planetDTO = planetMapper.toDto(planet);


        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAparicoesIsRequired() throws Exception {
        int databaseSizeBeforeTest = planetRepository.findAll().size();
        // set the field null
        planet.setAparicoes(null);

        // Create the Planet, which fails.
        PlanetDTO planetDTO = planetMapper.toDto(planet);


        restPlanetMockMvc.perform(post("/api/planets")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(planetDTO)))
            .andExpect(status().isBadRequest());

        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPlanets() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        // Get all the planetList
        restPlanetMockMvc.perform(get("/api/planets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planet.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].clima").value(hasItem(DEFAULT_CLIMA)))
            .andExpect(jsonPath("$.[*].terreno").value(hasItem(DEFAULT_TERRENO)))
            .andExpect(jsonPath("$.[*].aparicoes").value(hasItem(DEFAULT_APARICOES)));
    }
    
    @Test
    @Transactional
    public void getPlanet() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        // Get the planet
        restPlanetMockMvc.perform(get("/api/planets/findByid/{id}", planet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planet.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.clima").value(DEFAULT_CLIMA))
            .andExpect(jsonPath("$.terreno").value(DEFAULT_TERRENO))
            .andExpect(jsonPath("$.aparicoes").value(DEFAULT_APARICOES));
    }
    @Test
    @Transactional
    public void getNonExistingPlanet() throws Exception {
        // Get the planet
        restPlanetMockMvc.perform(get("/api/planets/findByid/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    public void deletePlanet() throws Exception {
        // Initialize the database
        planetRepository.saveAndFlush(planet);

        int databaseSizeBeforeDelete = planetRepository.findAll().size();

        // Delete the planet
        restPlanetMockMvc.perform(delete("/api/planets/{id}", planet.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Planet> planetList = planetRepository.findAll();
        assertThat(planetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
