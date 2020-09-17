package br.com.planets.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.planets.web.rest.TestUtil;

public class PlanetDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanetDTO.class);
        PlanetDTO planetDTO1 = new PlanetDTO();
        planetDTO1.setId(1L);
        PlanetDTO planetDTO2 = new PlanetDTO();
        assertThat(planetDTO1).isNotEqualTo(planetDTO2);
        planetDTO2.setId(planetDTO1.getId());
        assertThat(planetDTO1).isEqualTo(planetDTO2);
        planetDTO2.setId(2L);
        assertThat(planetDTO1).isNotEqualTo(planetDTO2);
        planetDTO1.setId(null);
        assertThat(planetDTO1).isNotEqualTo(planetDTO2);
    }
}
