package org.hibernate.bugs.entities;

import jakarta.persistence.Column;
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
 * The persistent class for the CUSTOMER_ATTRIBUTES database table.
 */
@Entity
@Table(name = "CUSTOMER_ATTRIBUTES")
@Getter
@Setter
@ToString(exclude = "customer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAttribute implements Serializable, IAttribute, Attribute {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private CustomerAttributePK id;

    @Column(name = "AUTOMATICALLYSET")
    private boolean auto;

    @Column(name = "CUSTOMERVALUE")
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", insertable = false, updatable = false)
    private Customer customer;

    public static CustomerAttribute of(String customerId, String key, String value, boolean auto) {
        CustomerAttribute attribute = new CustomerAttribute();
        attribute.setId(CustomerAttributePK.of(customerId, key));
        attribute.setValue(value);
        attribute.setAuto(auto);
        return attribute;
    }

    @Transient
    @Override
    public String getKey() {
        return getId().getKey();
    }

    @Override
    public String getEntityIdValue() {
        return getId().getCustomerId();
    }

    @Transient
    public String getCustomerId() {
        return getId().getCustomerId();
    }
}
