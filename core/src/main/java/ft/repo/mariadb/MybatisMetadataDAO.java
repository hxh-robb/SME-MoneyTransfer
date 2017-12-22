package ft.repo.mariadb;

import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MetadataDAO implementation for MariaDB
 */
@Repository
// @Mapper // Auto Render Mybatis Mapper
public class MybatisMetadataDAO implements MetadataDAO {
    private SqlSession sqlSession;
    private Logger LOG = LoggerFactory.getLogger(MybatisMetadataDAO.class);

    public MybatisMetadataDAO(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    @Override
    public boolean create(Metadata data) {
        try {
            sqlSession.insert("create", data);
            return true;
        } catch (Throwable throwable) {
            LOG.error("Create metadata error", throwable);
            return false;
        }
    }

    @Override
    public int update(Filter filter, Metadata data) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("filter", filter);
            param.put("data", data);
            return sqlSession.update("update", param);
        } catch (Throwable throwable) {
            LOG.error("Update metadata error", throwable);
            return -1;
        }
    }

    @Override
    public int delete(Filter filter) {
        try {
            // return sqlSession.update("update", param);
            return sqlSession.delete("delete", filter);
        } catch (Throwable throwable) {
            LOG.error("Delete metadata error", throwable);
            return -1;
        }
    }

    @Override
    public <E extends Metadata> List<E> list(Filter filter) {
        try {
            return sqlSession.selectList("list", filter);
        } catch (Throwable throwable) {
            LOG.error("List metadata error", throwable);
            return null;
        }
    }
}
