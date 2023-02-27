package com.atease.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BahRate.
 */
@Entity
@Table(name = "bah_rate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BahRate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "bahRate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "guest", "bahRate" }, allowSetters = true)
    private Set<PaymentGrade> paymentGrades = new HashSet<>();

    @OneToMany(mappedBy = "bahRate")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "state", "bahRate" }, allowSetters = true)
    private Set<City> cities = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BahRate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return this.amount;
    }

    public BahRate amount(Double amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return this.name;
    }

    public BahRate name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PaymentGrade> getPaymentGrades() {
        return this.paymentGrades;
    }

    public void setPaymentGrades(Set<PaymentGrade> paymentGrades) {
        if (this.paymentGrades != null) {
            this.paymentGrades.forEach(i -> i.setBahRate(null));
        }
        if (paymentGrades != null) {
            paymentGrades.forEach(i -> i.setBahRate(this));
        }
        this.paymentGrades = paymentGrades;
    }

    public BahRate paymentGrades(Set<PaymentGrade> paymentGrades) {
        this.setPaymentGrades(paymentGrades);
        return this;
    }

    public BahRate addPaymentGrade(PaymentGrade paymentGrade) {
        this.paymentGrades.add(paymentGrade);
        paymentGrade.setBahRate(this);
        return this;
    }

    public BahRate removePaymentGrade(PaymentGrade paymentGrade) {
        this.paymentGrades.remove(paymentGrade);
        paymentGrade.setBahRate(null);
        return this;
    }

    public Set<City> getCities() {
        return this.cities;
    }

    public void setCities(Set<City> cities) {
        if (this.cities != null) {
            this.cities.forEach(i -> i.setBahRate(null));
        }
        if (cities != null) {
            cities.forEach(i -> i.setBahRate(this));
        }
        this.cities = cities;
    }

    public BahRate cities(Set<City> cities) {
        this.setCities(cities);
        return this;
    }

    public BahRate addCity(City city) {
        this.cities.add(city);
        city.setBahRate(this);
        return this;
    }

    public BahRate removeCity(City city) {
        this.cities.remove(city);
        city.setBahRate(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BahRate)) {
            return false;
        }
        return id != null && id.equals(((BahRate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BahRate{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", name='" + getName() + "'" +
            "}";
    }
}
