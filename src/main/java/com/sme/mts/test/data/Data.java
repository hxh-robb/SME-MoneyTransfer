package com.sme.mts.test.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.mongodb.morphia.annotations.Id;

/**
 * Data base class
 */
public abstract class Data {
    /**
     * UUID
     */
    @Id
    protected String id;

    /**
     * Deleted flag
     */
    protected Boolean de = false;

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getDe() {
        return de;
    }

    public void setDe(Boolean de) {
        this.de = de;
    }

    @Override
    public String toString() {
        return "{" +
            "id='" + id + '\'' +
            ", de=" + de +
        '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        return new EqualsBuilder()
                .append(id, data.id)
                .append(de, data.de)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(de)
                .toHashCode();
    }
}
