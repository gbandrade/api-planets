package br.com.planets.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Planet.
 */
@Entity
@Table(name = "planet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Planet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nome", length = 150, nullable = false, unique = true)
    private String nome;

    @NotNull
    @Size(max = 150)
    @Column(name = "clima", length = 150, nullable = false)
    private String clima;

    @NotNull
    @Size(max = 150)
    @Column(name = "terreno", length = 150, nullable = false)
    private String terreno;

    @NotNull
    @Column(name = "aparicoes", nullable = false)
    private Integer aparicoes;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Planet nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClima() {
        return clima;
    }

    public Planet clima(String clima) {
        this.clima = clima;
        return this;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public String getTerreno() {
        return terreno;
    }

    public Planet terreno(String terreno) {
        this.terreno = terreno;
        return this;
    }

    public void setTerreno(String terreno) {
        this.terreno = terreno;
    }

    public Integer getAparicoes() {
        return aparicoes;
    }

    public Planet aparicoes(Integer aparicoes) {
        this.aparicoes = aparicoes;
        return this;
    }

    public void setAparicoes(Integer aparicoes) {
        this.aparicoes = aparicoes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Planet)) {
            return false;
        }
        return id != null && id.equals(((Planet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Planet{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", clima='" + getClima() + "'" +
            ", terreno='" + getTerreno() + "'" +
            ", aparicoes=" + getAparicoes() +
            "}";
    }
}
