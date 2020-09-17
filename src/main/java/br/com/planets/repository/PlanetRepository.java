package br.com.planets.repository;

import br.com.planets.domain.Planet;

import java.util.Optional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Planet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {
	
	Optional<Planet> findOneByNome(String nome);
}
