package ft.repo.mariadb;

import ft.repo.MetadataDAO;
import ft.spec.model.Metadata;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * TODO
 */
@Repository
// @Mapper // Auto Render Mybatis Mapper
public class MybatisMetadataDAO implements MetadataDAO {
    private SqlSession sqlSession;

    public MybatisMetadataDAO(SqlSession sqlSession){
        this.sqlSession = sqlSession;
    }

    @Override
    public boolean create(Metadata data) {
//        try {
            sqlSession.insert("create", data);
            return true;
//        } catch (Throwable throwable) {
//            return false;
//        }
    }

    @Override
    public int update(Filter filter, Metadata data) {
        return 0;
    }

    @Override
    public int delete(Filter filter) {
        return 0;
    }

    @Override
    public <E extends Metadata> List<E> list(Filter filter) {
        return sqlSession.selectList("list", filter);
    }
    // Metadata getById(String id);
}
