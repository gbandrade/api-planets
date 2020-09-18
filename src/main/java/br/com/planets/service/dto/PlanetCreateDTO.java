package br.com.planets.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PlanetCreateDTO {

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
            ", terreno=" + getTerreno() +
            "}";
    }

}
