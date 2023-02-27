package com.atease.booking.service.dto;

import com.atease.booking.domain.enumeration.Relation;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.atease.booking.domain.FamilyMember} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FamilyMemberDTO implements Serializable {

    private Long id;

    private String fullname;

    private LocalDate dob;

    private Relation relation;

    private GuestDTO guest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Relation getRelation() {
        return relation;
    }

    public void setRelation(Relation relation) {
        this.relation = relation;
    }

    public GuestDTO getGuest() {
        return guest;
    }

    public void setGuest(GuestDTO guest) {
        this.guest = guest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyMemberDTO)) {
            return false;
        }

        FamilyMemberDTO familyMemberDTO = (FamilyMemberDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, familyMemberDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyMemberDTO{" +
            "id=" + getId() +
            ", fullname='" + getFullname() + "'" +
            ", dob='" + getDob() + "'" +
            ", relation='" + getRelation() + "'" +
            ", guest=" + getGuest() +
            "}";
    }
}
