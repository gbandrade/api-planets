package br.com.planets.service;

import br.com.planets.service.dto.PlanetDTO;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link br.com.planets.domain.Planet}.
 */
public interface PlanetService {

    /**
     * Save a planet.
     *
     * @param planetDTO the entity to save.
     * @return the persisted entity.
     */
    PlanetDTO save(PlanetDTO planetDTO);

    /**
     * Get all the planets.
     *
     * @return the list of entities.
     */
    List<PlanetDTO> findAll();

    /**
     * Get all the planets.
     *
     * @return the list of entities.
     */
    Page<PlanetDTO> findAll(Pageable pageable);    

    /**
     * Get the "id" planet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlanetDTO> findOne(Long id);

    /**
     * Get the "name" planet.
     *
     * @param name the name of the entity.
     * @return the entity.
     */
    Optional<PlanetDTO> findOneByNome(String nome);
    
    /**
     * Delete the "id" planet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
