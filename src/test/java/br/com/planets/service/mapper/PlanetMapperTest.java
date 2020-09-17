package br.com.planets.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanetMapperTest {

    private PlanetMapper planetMapper;

    @BeforeEach
    public void setUp() {
        planetMapper = new PlanetMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(planetMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(planetMapper.fromId(null)).isNull();
    }
}
