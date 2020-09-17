package br.com.planets.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.com.planets.domain.Planet} entity.
 */
public class PlanetDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(max = 150)
    private String nome;

    @NotNull
    @Size(max = 150)
    private String clima;

    @NotNull
    @Size(max = 150)
    private String terreno;

    @NotNull
    private Integer aparicoes;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getTerreno() {
        return terreno;
    }

    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }

    public Integer getAparicoes() {
        return aparicoes;
    }

    public void setAparicoes(Integer aparicoes) {
        this.aparicoes = aparicoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanetDTO)) {
            return false;
        }

        return id != null && id.equals(((PlanetDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanetDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", clima='" + getClima() + "'" +
            ", terreno='" + getTerreno() + "'" +
            ", aparicoes=" + getAparicoes() +
            "}";
    }
}
