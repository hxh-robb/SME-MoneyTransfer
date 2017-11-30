package ft.spec.service;

import ft.spec.model.DepositAddon;
import ft.spec.model.Metadata;

/**
 * Metadata service
 */
public interface MetadataService {
    /**
     * Return the list of supported fund account types
     * @param subject The operator
     * @param catalog Catalog filter
     * @return
     */
    Metadata[] list(String subject, String catalog);

    /**
     * Create new metadata
     * @param subject The operator
     * @param metadata Metadata
     * @return
     */
    Result create(String subject, Metadata metadata);

    /**
     * Delete specific metadata
     * @param subject The operator
     * @param catalog Catalog of the metadata which to be deleted
     * @param name  Name of the metadata which to be deleted
     * @return
     */
    Result delete(String subject, String catalog, String name);

    /**
     * Metadata service result code
     */
    interface Code extends Result.Code {
        // TODO
    }
}
