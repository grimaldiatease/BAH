package com.atease.booking.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.atease.booking.domain.PaymentGrade} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentGradeDTO implements Serializable {

    private Long id;

    private String name;

    private String series;

    private String description;

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

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentGradeDTO)) {
            return false;
        }

        PaymentGradeDTO paymentGradeDTO = (PaymentGradeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentGradeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentGradeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", series='" + getSeries() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
