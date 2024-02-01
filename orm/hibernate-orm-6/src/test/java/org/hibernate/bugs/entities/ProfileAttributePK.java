package org.hibernate.bugs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * The primary key class for the PROFILE_ATTRIBUTES database table.
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAttributePK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "PROFILEID", updatable = false)
    private String profileId;

    @Column(name = "PROFILEKEY", updatable = false)
    private String key;


    public static ProfileAttributePK of(String profileId, String key) {
        return new ProfileAttributePK(profileId, key);
    }
}
