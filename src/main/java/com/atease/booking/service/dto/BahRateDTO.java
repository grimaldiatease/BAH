package com.atease.booking.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.atease.booking.domain.BahRate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BahRateDTO implements Serializable {

    private Long id;

    private Double amount;

    private String name;

    private PaymentGradeDTO paymentGrade;

    private CityDTO city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentGradeDTO getPaymentGrade() {
        return paymentGrade;
    }

    public void setPaymentGrade(PaymentGradeDTO paymentGrade) {
        this.paymentGrade = paymentGrade;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BahRateDTO)) {
            return false;
        }

        BahRateDTO bahRateDTO = (BahRateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bahRateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BahRateDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", name='" + getName() + "'" +
            ", paymentGrade=" + getPaymentGrade() +
            ", city=" + getCity() +
            "}";
    }
}
