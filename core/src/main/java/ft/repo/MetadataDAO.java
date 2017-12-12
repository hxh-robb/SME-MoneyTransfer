package ft.repo;

import ft.spec.model.DepositAddon;
import ft.spec.model.Metadata;

/**
 * Metadata DAO
 */
public interface MetadataDAO extends DAO<Metadata, MetadataDAO.Filter> {
    /**
     * Metadata filter
     */
    class Filter {
        /**
         * @see Metadata.CATALOG
         */
        public String catalog;

        /**
         * Metadata name
         */
        public String name;
    }

    /**
     * Deposit addon filter
     */
    final class DepositAddonFilter extends Filter {
        public DepositAddon.Mode mode;
        public DepositAddon.Type type;
    }
}
