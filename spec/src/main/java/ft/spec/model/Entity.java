package ft.spec.model;

import java.util.Date;

/**
 * Entity base class
 */
public abstract class Entity {
    /**
     * <pre>
     * Entity unique identity
     * "id" stands for "IDentity"
     * </pre>
     */
    protected Long id;

    /**
     * <pre>
     * Entity updated timestamp
     * "ts" stands for "TimeStamp"
     * </pre>
     */
    protected Date ts;

    /**
     * <pre>
     * Entity belongs to which business object(organization,user,platform,etc.)
     * "bt" stands for "Belongs To"
     * </pre>
     */
    protected Object bt;

    /**
     * <pre>
     * Deleted state of the entity
     * </pre>
     */
    protected Boolean de;
}
