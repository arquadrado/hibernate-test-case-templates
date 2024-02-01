package org.hibernate.bugs.entities;

public interface IAttribute {

    String getKey();

    String getValue();

    void setValue(String value);

    boolean isAuto();

    void setAuto(boolean auto);
}
