package org.hibernate.bugs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DiscriminatorFormula;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;


/**
 * The persistent class for the CUSTOMERS database table.
 */
@Entity
@Table(name = "CUSTOMERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(Constants.QUERY_STATE_FILTER)
@Getter
@Setter
@ToString(exclude = "attributes")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CUSTOMER_ID")
    private String id;

    @Column(name = "CREATETS")
    private Timestamp createdOn;

    @Column(name = "DELETETS")
    private Timestamp deletedOn;

    @Column(name = "DISABLETS")
    private Timestamp disabledOn;

    @Column(name = "LASTMODIFIEDATTRIBUTESTS")
    private Timestamp attributesLastModifiedOn;

    @Column(name = "LASTMODIFIEDCOMMENT")
    private String lastModifiedComment;

    @Column(name = "LASTMODIFIEDTS")
    private Timestamp lastModifiedOn;

    @Column(name = "LASTMODIFIEDUSER")
    private String lastModifiedBy;

    @Column(name = "NAMELONG")
    private String nameLong;

    @Column(name = "NAMESHORT")
    private String nameShort;

    //bi-directional many-to-one association to CustomerAttribute
    @OneToMany(mappedBy = "customer")
    @SuppressWarnings("squid:S1948") // JPA Entity Require Interface therefore False Positive.
    private List<CustomerAttribute> attributes;

    //bi-directional many-to-one association to Profile
    @OneToMany(mappedBy = "customer")
    @SuppressWarnings("squid:S1948") // JPA Entity Require Interface therefore False Positive.
    private List<Profile> profile;

    @PrePersist
    public void beforePersisting() {
        this.setCreatedOn(TimestampUtil.lastModifiedAtAsTimestamp(Instant.now()));
    }

    public boolean isDeleted() {
        return this.getDeletedOn() != null && this.getDeletedOn().after(new Timestamp(0));
    }

    public boolean isDisabled() {
        return this.getDisabledOn() != null && this.getDisabledOn().after(new Timestamp(0));
    }

    public boolean isActive() {
        return !isDeleted() && !isDisabled();
    }

    public void delete() {
        this.setDeletedOn(TimestampUtil.lastModifiedAtAsTimestamp(Instant.now()));
    }

    public void disable() {
        this.setDisabledOn(TimestampUtil.lastModifiedAtAsTimestamp(Instant.now()));
    }

    public void activate() {
        this.setDeletedOn(new Timestamp(0));
        this.setDeletedOn(new Timestamp(0));
    }

    public void touchLastModified(String user) {
        this.setLastModifiedBy(Objects.requireNonNull(user));
        this.setLastModifiedOn(TimestampUtil.lastModifiedAtAsTimestamp(Instant.now()));
    }

    @Entity
    @DiscriminatorValue("DISABLED")
    public static class Disabled extends Customer { }

    @Entity
    @DiscriminatorValue("ACTIVE")
    public static class Active extends Customer {
        private static final long serialVersionUID = 1L;
    }

    @Entity
    @DiscriminatorValue("DELETED")
    public static class Deleted extends Customer {
        private static final long serialVersionUID = 1L;
    }

}
