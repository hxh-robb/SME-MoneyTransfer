package ft.spec.service;

import ft.spec.model.TransferAddon;
import ft.spec.model.Metadata;

import java.util.List;

/**
 * Metadata service
 */
public interface MetadataService extends EntityService<Metadata> {
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
