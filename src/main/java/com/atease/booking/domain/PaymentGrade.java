package com.atease.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PaymentGrade.
 */
@Entity
@Table(name = "payment_grade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentGrade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "series")
    private String series;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = { "paymentGrades", "installations", "departments" }, allowSetters = true)
    private Guest guest;

    @ManyToOne
    @JsonIgnoreProperties(value = { "paymentGrades", "cities" }, allowSetters = true)
    private BahRate bahRate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentGrade id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public PaymentGrade name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeries() {
        return this.series;
    }

    public PaymentGrade series(String series) {
        this.setSeries(series);
        return this;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getDescription() {
        return this.description;
    }

    public PaymentGrade description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Guest getGuest() {
        return this.guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public PaymentGrade guest(Guest guest) {
        this.setGuest(guest);
        return this;
    }

    public BahRate getBahRate() {
        return this.bahRate;
    }

    public void setBahRate(BahRate bahRate) {
        this.bahRate = bahRate;
    }

    public PaymentGrade bahRate(BahRate bahRate) {
        this.setBahRate(bahRate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentGrade)) {
            return false;
        }
        return id != null && id.equals(((PaymentGrade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentGrade{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", series='" + getSeries() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
