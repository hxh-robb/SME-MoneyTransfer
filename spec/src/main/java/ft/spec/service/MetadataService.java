package ft.spec.service;

import ft.spec.model.TransferAddon;
import ft.spec.model.Metadata;

import java.util.List;

/**
 * Metadata service
 */
public interface MetadataService {
//    /**
//     * Return the list of supported fund account types
//     * @param subject The operator
//     * @param catalog Catalog filter
//     * @return
//     * @see ft.spec.model.Metadata.CATALOG
//     */
//    List<Metadata> list(String subject, String catalog);

    /**
     * Create new metadata
     * @param subject The operator
     * @param metadata Metadata
     * @return
     */
    Result create(String subject, Metadata metadata);

    /**
     * Create specific metadata
     * @param subject The operator
     * @param metadata Metadata
     * @return
     */
    Result update(String subject, Metadata metadata);

    /**
     * Delete specific metadata
     * @param subject The operator
     * @param id  ID of the metadata
     * @return
     */
    Result delete(String subject, String id);

    /**
     * List supported transfer addons.
     * Transfer addons are the prerequisite metadata for creating fund account
     * @param subject
     * @return
     */
    List<TransferAddon> supportedTransferAddons(String subject);

    /**
     * Metadata service result code
     */
    interface Code extends Result.Code {
        // TODO
    }
}
