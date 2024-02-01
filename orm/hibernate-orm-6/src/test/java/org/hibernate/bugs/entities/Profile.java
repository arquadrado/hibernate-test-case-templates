package org.hibernate.bugs.entities;

import jakarta.persistence.*;
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
import java.util.Optional;


@Entity
@Table(name = "PROFILES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(Constants.QUERY_STATE_FILTER)
@Getter
@Setter
@ToString(exclude = {"customer", "attributes"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
    @NamedQuery(name = "Profile.findOneActiveByCustomerAndProfileId", query = "FROM Profile$Active p WHERE p.customer.id = :customerId AND p.id = :profileId")
})
@NamedEntityGraphs({
    @NamedEntityGraph(name = "Profile.full", attributeNodes = {
        @NamedAttributeNode(value = "customer"),
        @NamedAttributeNode(value = "commonDisplayNameAttribute")
    })
})
public class Profile implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PROFILEID")
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

    @Column(name = "PROFILENAME")
    private String name;


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "profile")
    private ProfileAttribute.CommonDisplayName commonDisplayNameAttribute;

    public Optional<String> getCommonDisplayName() {
        return Optional.ofNullable(commonDisplayNameAttribute)
                       .map(ProfileAttribute.CommonDisplayName::getValue);
    }

    @OneToMany(mappedBy = "profile")
    @SuppressWarnings("squid:S1948") // JPA Entity Require Interface therefore False Positive.
    private List<ProfileAttribute> attributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;


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
        this.setLastModifiedOn(TimestampUtil.lastModifiedAtAsTimestamp(
            Instant.now()));
    }

    public String getDisplayTitle() {
        return this.getCommonDisplayName().orElse(this.getName());
    }

    @Entity
    @DiscriminatorValue("DISABLED")
    public static class Disabled extends Profile {

    }

    @Entity
    @DiscriminatorValue("ACTIVE")
    public static class Active extends Profile {
        private static final long serialVersionUID = 1L;

    }

    @Entity
    @DiscriminatorValue("DELETED")
    public static class Deleted extends Profile {
        private static final long serialVersionUID = 1L;

    }
}
