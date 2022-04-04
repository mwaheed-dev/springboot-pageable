package com.scb.fimob.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Ethnicity.
 */
@Entity
@Table(name = "ethnicity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Ethnicity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "urdu_name")
    private String urduName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ethnicity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Ethnicity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrduName() {
        return this.urduName;
    }

    public Ethnicity urduName(String urduName) {
        this.setUrduName(urduName);
        return this;
    }

    public void setUrduName(String urduName) {
        this.urduName = urduName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ethnicity)) {
            return false;
        }
        return id != null && id.equals(((Ethnicity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ethnicity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", urduName='" + getUrduName() + "'" +
            "}";
    }
}
