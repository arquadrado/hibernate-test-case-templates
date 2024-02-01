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
 * The primary key class for the CUSTOMER_ATTRIBUTES database table.
 */
@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAttributePK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "CUSTOMER_ID", insertable = false, updatable = false)
    private String customerId;

    @Column(name = "CUSTOMERKEY")
    private String key;


    public static CustomerAttributePK of(String customerId, String key) {
        return new CustomerAttributePK(customerId, key);
    }
}
