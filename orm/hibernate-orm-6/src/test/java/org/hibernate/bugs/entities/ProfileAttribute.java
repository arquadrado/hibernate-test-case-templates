package org.hibernate.bugs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
@Getter
@Setter
@ToString(exclude = "profile")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileAttribute implements Serializable, IAttribute, Attribute {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProfileAttributePK id;

    @Column(name = "AUTOMATICALLYSET")
    private boolean auto;

    @Column(name = "PROFILEVALUE")
    private String value;

    //bi-directional many-to-one association to Profile
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PROFILEID", insertable = false, updatable = false)
    private Profile profile;

    public static ProfileAttribute of(String profileId, String key, String value, boolean auto) {
        ProfileAttribute attribute = new ProfileAttribute();
        attribute.setId(ProfileAttributePK.of(profileId, key));
        attribute.setValue(value);
        attribute.setAuto(auto);
        return attribute;
    }

    @Transient
    @Override
    public String getKey() {
        return getId().getKey();
    }

    @Transient
    @Override
    public String getEntityIdValue() {
        return getId().getProfileId();
    }



    @Entity
    @DiscriminatorValue("common.displayname")
    public static class CommonDisplayName extends ProfileAttributeSingle {
        private static final long serialVersionUID = 1L;

    }
}
