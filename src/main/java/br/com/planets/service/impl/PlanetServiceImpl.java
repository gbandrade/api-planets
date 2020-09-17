package br.com.planets.service.impl;

import br.com.planets.service.PlanetService;
import br.com.planets.domain.Planet;
import br.com.planets.repository.PlanetRepository;
import br.com.planets.service.dto.PlanetDTO;
import br.com.planets.service.mapper.PlanetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Planet}.
 */
@Service
@Transactional
public class PlanetServiceImpl implements PlanetService {

    private final Logger log = LoggerFactory.getLogger(PlanetServiceImpl.class);

    private final PlanetRepository planetRepository;

    private final PlanetMapper planetMapper;

    public PlanetServiceImpl(PlanetRepository planetRepository, PlanetMapper planetMapper) {
        this.planetRepository = planetRepository;
        this.planetMapper = planetMapper;
    }

    @Override
    public PlanetDTO save(PlanetDTO planetDTO) {
        log.debug("Request to save Planet : {}", planetDTO);
        Planet planet = planetMapper.toEntity(planetDTO);
        planet = planetRepository.save(planet);
        return planetMapper.toDto(planet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanetDTO> findAll() {
        log.debug("Request to get all Planets");
        return planetRepository.findAll().stream()
            .map(planetMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<PlanetDTO> findOne(Long id) {
        log.debug("Request to get Planet : {}", id);
        return planetRepository.findById(id)
            .map(planetMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Planet : {}", id);
        planetRepository.deleteById(id);
    }

	@Override
	public Page<PlanetDTO> findAll(Pageable pageable) {
		log.debug("Request to get all Planets");
		return planetRepository.findAll(pageable)
				.map(planetMapper::toDto);
	}

	@Override
	public Optional<PlanetDTO> findOneByNome(String nome) {
		log.debug("Request to get Planet by name: {}", nome);
		return planetRepository.findOneByNome(nome)
				.map(planetMapper::toDto);
	}
}
