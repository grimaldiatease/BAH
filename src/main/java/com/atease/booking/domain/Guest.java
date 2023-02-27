package com.atease.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Guest.
 */
@Entity
@Table(name = "guest")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Guest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @OneToMany(mappedBy = "guest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "guest", "bahRate" }, allowSetters = true)
    private Set<PaymentGrade> paymentGrades = new HashSet<>();

    @OneToMany(mappedBy = "guest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "guest" }, allowSetters = true)
    private Set<Installation> installations = new HashSet<>();

    @OneToMany(mappedBy = "guest")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "guest" }, allowSetters = true)
    private Set<Department> departments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Guest id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Guest firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Guest lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return this.phone;
    }

    public Guest phone(String phone) {
        this.setPhone(phone);
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Guest email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return this.address1;
    }

    public Guest address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public Guest address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return this.country;
    }

    public Guest country(String country) {
        this.setCountry(country);
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return this.city;
    }

    public Guest city(String city) {
        this.setCity(city);
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return this.state;
    }

    public Guest state(String state) {
        this.setState(state);
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return this.zip;
    }

    public Guest zip(String zip) {
        this.setZip(zip);
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Set<PaymentGrade> getPaymentGrades() {
        return this.paymentGrades;
    }

    public void setPaymentGrades(Set<PaymentGrade> paymentGrades) {
        if (this.paymentGrades != null) {
            this.paymentGrades.forEach(i -> i.setGuest(null));
        }
        if (paymentGrades != null) {
            paymentGrades.forEach(i -> i.setGuest(this));
        }
        this.paymentGrades = paymentGrades;
    }

    public Guest paymentGrades(Set<PaymentGrade> paymentGrades) {
        this.setPaymentGrades(paymentGrades);
        return this;
    }

    public Guest addPaymentGrade(PaymentGrade paymentGrade) {
        this.paymentGrades.add(paymentGrade);
        paymentGrade.setGuest(this);
        return this;
    }

    public Guest removePaymentGrade(PaymentGrade paymentGrade) {
        this.paymentGrades.remove(paymentGrade);
        paymentGrade.setGuest(null);
        return this;
    }

    public Set<Installation> getInstallations() {
        return this.installations;
    }

    public void setInstallations(Set<Installation> installations) {
        if (this.installations != null) {
            this.installations.forEach(i -> i.setGuest(null));
        }
        if (installations != null) {
            installations.forEach(i -> i.setGuest(this));
        }
        this.installations = installations;
    }

    public Guest installations(Set<Installation> installations) {
        this.setInstallations(installations);
        return this;
    }

    public Guest addInstallation(Installation installation) {
        this.installations.add(installation);
        installation.setGuest(this);
        return this;
    }

    public Guest removeInstallation(Installation installation) {
        this.installations.remove(installation);
        installation.setGuest(null);
        return this;
    }

    public Set<Department> getDepartments() {
        return this.departments;
    }

    public void setDepartments(Set<Department> departments) {
        if (this.departments != null) {
            this.departments.forEach(i -> i.setGuest(null));
        }
        if (departments != null) {
            departments.forEach(i -> i.setGuest(this));
        }
        this.departments = departments;
    }

    public Guest departments(Set<Department> departments) {
        this.setDepartments(departments);
        return this;
    }

    public Guest addDepartment(Department department) {
        this.departments.add(department);
        department.setGuest(this);
        return this;
    }

    public Guest removeDepartment(Department department) {
        this.departments.remove(department);
        department.setGuest(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Guest)) {
            return false;
        }
        return id != null && id.equals(((Guest) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Guest{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", country='" + getCountry() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", zip='" + getZip() + "'" +
            "}";
    }
}
