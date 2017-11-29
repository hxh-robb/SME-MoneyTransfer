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
        public String catalog;
        public String name;
    }
}
