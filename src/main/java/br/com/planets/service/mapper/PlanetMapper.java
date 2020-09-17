package br.com.planets.service.mapper;


import br.com.planets.domain.*;
import br.com.planets.service.dto.PlanetDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Planet} and its DTO {@link PlanetDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlanetMapper extends EntityMapper<PlanetDTO, Planet> {



    default Planet fromId(Long id) {
        if (id == null) {
            return null;
        }
        Planet planet = new Planet();
        planet.setId(id);
        return planet;
    }
}
