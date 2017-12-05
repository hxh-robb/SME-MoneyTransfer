package ft.repo;

import ft.spec.model.Metadata;

/**
 * Metadata DAO
 */
public interface MetadataDAO extends DAO<Metadata, MetadataDAO.Filter> {
    /**
     * Metadata filter
     */
    final class Filter {
        /**
         * @see Metadata.CATALOG
         */
        public String catalog;

        /**
         * Metadata name
         */
        public String name;
    }
}
