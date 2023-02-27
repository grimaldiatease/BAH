package com.atease.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A City.
 */
@Entity
@Table(name = "city")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "city" }, allowSetters = true)
    private Set<State> states = new HashSet<>();

    @OneToMany(mappedBy = "city")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paymentGrade", "city" }, allowSetters = true)
    private Set<BahRate> bahRates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public City id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public City name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public City description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<State> getStates() {
        return this.states;
    }

    public void setStates(Set<State> states) {
        if (this.states != null) {
            this.states.forEach(i -> i.setCity(null));
        }
        if (states != null) {
            states.forEach(i -> i.setCity(this));
        }
        this.states = states;
    }

    public City states(Set<State> states) {
        this.setStates(states);
        return this;
    }

    public City addState(State state) {
        this.states.add(state);
        state.setCity(this);
        return this;
    }

    public City removeState(State state) {
        this.states.remove(state);
        state.setCity(null);
        return this;
    }

    public Set<BahRate> getBahRates() {
        return this.bahRates;
    }

    public void setBahRates(Set<BahRate> bahRates) {
        if (this.bahRates != null) {
            this.bahRates.forEach(i -> i.setCity(null));
        }
        if (bahRates != null) {
            bahRates.forEach(i -> i.setCity(this));
        }
        this.bahRates = bahRates;
    }

    public City bahRates(Set<BahRate> bahRates) {
        this.setBahRates(bahRates);
        return this;
    }

    public City addBahRate(BahRate bahRate) {
        this.bahRates.add(bahRate);
        bahRate.setCity(this);
        return this;
    }

    public City removeBahRate(BahRate bahRate) {
        this.bahRates.remove(bahRate);
        bahRate.setCity(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return id != null && id.equals(((City) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
