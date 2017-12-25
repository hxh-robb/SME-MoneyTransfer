package ft.repo.mariadb;

import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import org.springframework.stereotype.Repository;

/**
 * MetadataDAO implementation for MariaDB
 */
@Repository // @Mapper // Auto Render Mybatis Mapper
public class MybatisMetadataDAO extends MybatisDAO<Metadata, MetadataDAO.Filter> implements MetadataDAO {

    /**
     * Constructor
     */
    public MybatisMetadataDAO() {
        super("MetadataMapper");
    }
}
