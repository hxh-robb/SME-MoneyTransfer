package com.sme.mts.review.entity;

import java.text.MessageFormat;

/**
 * Metadata base class
 */
public class Metadata extends Entity {

    /**
     * Limit the constructor to subclasses only, force subclasses to specific the catalog
     */
    public Metadata() {
        super();
    }

    public Metadata(boolean init, String catalog) {
        super(init);
        this.catalog = catalog;
    }

    /**
     * Limit the constructor to subclasses only, force subclasses to specific the catalog
     * @param catalog
     * @see CATALOG
     */
    protected Metadata(String catalog) {
        this.catalog = catalog;
    }

    /**
     * <pre>
     * Catalog of the metadata entry
     * </pre>
     * @see CATALOG
     */
    protected String catalog;

    /**
     * <pre>
     * Name of the metadata entry
     * </pre>
     */
    protected String name;

    /**
     * <pre>
     * Description of the metadata entry
     * </pre>
     */
    protected String description;

    /**
     * <pre>
     * Metadata value
     * </pre>
     */
    protected String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return toString(true);
    }

    protected String toString(boolean complete) {
        String fields =  super.toString(false) +
                ", catalog=" + catalog +
                ", name=" + name +
                ", description=" + description +
                ", value=" + value ;
        return complete ? MessageFormat.format("Metadata'{'{0}'}'", fields) : fields;
    }

    /**
     * Metadata catalog constants
     */
    public interface CATALOG {
        /**
         * Undefined catalog
         */
        String UNDEFINED = "Undefined";

        /**
         * Fund account type catalog
         */
        String TRANSFER_ADDON = "TransferAddon";
    }
}
