package com.atease.booking.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.atease.booking.domain.City} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CityDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private StateDTO state;

    private BahRateDTO bahRate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StateDTO getState() {
        return state;
    }

    public void setState(StateDTO state) {
        this.state = state;
    }

    public BahRateDTO getBahRate() {
        return bahRate;
    }

    public void setBahRate(BahRateDTO bahRate) {
        this.bahRate = bahRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityDTO)) {
            return false;
        }

        CityDTO cityDTO = (CityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", state=" + getState() +
            ", bahRate=" + getBahRate() +
            "}";
    }
}
