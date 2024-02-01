package org.hibernate.bugs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * The persistent class for the PROFILE_ATTRIBUTES database table.
 */
@Entity
@Table(name = "PROFILE_ATTRIBUTES")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(length = 254, name = "PROFILEKEY", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
@ToString(exclude = "profile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileAttributeSingle implements Serializable, IAttribute, Attribute {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PROFILEID", updatable = false)
    private String profileId;

    @Column(name = "PROFILEKEY", updatable = false, insertable = false)
    private String key;

    @Column(name = "AUTOMATICALLYSET")
    private boolean auto;

    @Column(name = "PROFILEVALUE")
    private String value;

    //bi-directional many-to-one association to Profile
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFILEID", insertable = false, updatable = false)
    private Profile profile;

    public static ProfileAttributeSingle of(String profileId, String key, String value, boolean auto) {
        ProfileAttributeSingle attribute = new ProfileAttributeSingle();
        attribute.setProfileId(profileId);
        attribute.setKey(key);
        attribute.setValue(value);
        attribute.setAuto(auto);
        return attribute;
    }

    @Transient
    @Override
    public String getEntityIdValue() {
        return this.getProfileId();
    }


}
