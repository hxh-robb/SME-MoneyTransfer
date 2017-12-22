package ft.repo;

import ft.spec.model.TransferAddon;
import ft.spec.model.Metadata;

/**
 * Metadata DAO
 */
public interface MetadataDAO extends DAO<Metadata, MetadataDAO.Filter> {

    /**
     * Metadata filter
     */
    class Filter extends DAO.Filter {

        /**
         * @see Metadata.CATALOG
         */
        public String catalog;

        /**
         * Metadata name
         */
        public String name;

        /**
         * Metadata value
         */
        public String value;
    }

    /**
     * Deposit addon filter
     */
    final class TransferAddonFilter extends Filter {
        public TransferAddonFilter(){
            this.catalog = Metadata.CATALOG.FUND_ACCOUNT_TYPE;
        }
        public TransferAddon.Mode mode;
        public TransferAddon.Type type;
    }
}
