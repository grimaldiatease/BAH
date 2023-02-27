package com.atease.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @OneToMany(mappedBy = "paymentGrade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "familyMembers", "paymentGrade", "installation", "department" }, allowSetters = true)
    private Set<Guest> guests = new HashSet<>();

    @OneToMany(mappedBy = "paymentGrade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paymentGrade", "city" }, allowSetters = true)
    private Set<BahRate> bahRates = new HashSet<>();

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

    public Set<Guest> getGuests() {
        return this.guests;
    }

    public void setGuests(Set<Guest> guests) {
        if (this.guests != null) {
            this.guests.forEach(i -> i.setPaymentGrade(null));
        }
        if (guests != null) {
            guests.forEach(i -> i.setPaymentGrade(this));
        }
        this.guests = guests;
    }

    public PaymentGrade guests(Set<Guest> guests) {
        this.setGuests(guests);
        return this;
    }

    public PaymentGrade addGuest(Guest guest) {
        this.guests.add(guest);
        guest.setPaymentGrade(this);
        return this;
    }

    public PaymentGrade removeGuest(Guest guest) {
        this.guests.remove(guest);
        guest.setPaymentGrade(null);
        return this;
    }

    public Set<BahRate> getBahRates() {
        return this.bahRates;
    }

    public void setBahRates(Set<BahRate> bahRates) {
        if (this.bahRates != null) {
            this.bahRates.forEach(i -> i.setPaymentGrade(null));
        }
        if (bahRates != null) {
            bahRates.forEach(i -> i.setPaymentGrade(this));
        }
        this.bahRates = bahRates;
    }

    public PaymentGrade bahRates(Set<BahRate> bahRates) {
        this.setBahRates(bahRates);
        return this;
    }

    public PaymentGrade addBahRate(BahRate bahRate) {
        this.bahRates.add(bahRate);
        bahRate.setPaymentGrade(this);
        return this;
    }

    public PaymentGrade removeBahRate(BahRate bahRate) {
        this.bahRates.remove(bahRate);
        bahRate.setPaymentGrade(null);
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
