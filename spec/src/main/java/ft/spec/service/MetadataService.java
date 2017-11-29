package ft.spec.service;

import ft.spec.model.DepositAddon;
import ft.spec.model.Metadata;

/**
 * Metadata service
 */
public interface MetadataService {
    /**
     * Return the list of supported fund account types
     * @return Supported fund account types
     */
    Metadata[] list(String catalog);

    /**
     * Create new metadata
     * @param metadata
     * @return
     * @see Code
     */
    Result create(Metadata metadata);

    /**
     * Delete specific metadata
     * @param catalog
     * @param name
     * @return
     * @see Code
     */
    Result delete(String catalog, String name);

    /**
     * Result code of metadata service
     */
    interface Code extends Result.Code {
        // TODO
    }
}
