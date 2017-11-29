package ft.spec.model;

/**
 * Metadata base class
 */
public abstract class Metadata {

    /**
     * Limit the constructor to subclasses only, force subclasses to specific the catalog
     * @param catalog
     */
    protected Metadata(String catalog) {
        this.catalog = catalog;
    }

    /**
     * <pre>
     * Catalog of the metadata entry
     * </pre>
     */
    protected final String catalog;

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

    /**
     * <pre>
     * Whether or not the metadata entry is enabled
     * </pre>
     */
    protected Boolean enabled = true;

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
