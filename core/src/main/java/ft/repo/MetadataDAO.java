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
        public Filter() {
            this.de = false;
        }

        /**
         * @see Metadata.CATALOG
         */
        public String catalog;
        public String name;
        public String value;
    }

    /**
     * Deposit addon filter
     */
    final class TransferAddonFilter extends Filter {
        public TransferAddonFilter(){
            super();
            this.catalog = Metadata.CATALOG.TRANSFER_ADDON;
        }
        public TransferAddon.Mode mode;
        public TransferAddon.Type type;
    }
}
