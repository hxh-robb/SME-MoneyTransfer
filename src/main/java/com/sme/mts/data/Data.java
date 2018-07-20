package com.sme.mts.data;

/**
 * Data base class
 */
public abstract class Data {
    /**
     * UUID
     */
    protected String id;

    /**
     * Deleted flag
     */
    protected Boolean de;

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
}
