package ft.spec.service;

import ft.spec.model.Metadata;

import java.io.Serializable;

/**
 * Service base interface
 * @param <T> Entity type
 */
public interface Service<T extends Serializable> extends Serializable {
    /**
     * Create new entity
     * @param subject The operator
     * @param entity The entity
     * @return
     */
    Result create(String subject, T entity);

    /**
     * Update specific entity
     * @param subject The operator
     * @param entity The entity
     * @return
     */
    Result update(String subject, T entity);

    /**
     *  Delete specific entity
     * @param subject The operator
     * @param id  ID of the entity
     * @return
     */
    Result delete(String subject, String id);
}
