package ft.repo.mariadb;

import ft.repo.DAO;
import ft.spec.model.Entity;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO implementation using Mybatis
 * @param <T> Entity Type
 * @param <F> Filter Type
 */
public abstract class MybatisDAO<T extends Entity, F extends DAO.Filter> implements DAO<T,F> {

    protected final Logger LOG;

    @Autowired
    protected SqlSession sqlSession;

    /**
     * Mybatis mapper namespace
     */
    protected final String namespace;

    protected final String createStatement;
    protected final String deleteStatement;
    protected final String updateStatement;
    protected final String listStatement;


    /**
     * This constructor should be called by sub-classes only
     */
    protected MybatisDAO(String namespace) {
        this.LOG = LoggerFactory.getLogger(this.getClass());
        this.namespace = namespace;

        String statementPattern = "{0}.{1}";
        this.createStatement = MessageFormat.format(statementPattern, this.namespace, "create");
        this.deleteStatement = MessageFormat.format(statementPattern, this.namespace, "delete");
        this.updateStatement = MessageFormat.format(statementPattern, this.namespace, "update");
        this.listStatement = MessageFormat.format(statementPattern, this.namespace, "list");
    }

    @Override
    public boolean create(T data) {
        try {
            sqlSession.insert(createStatement, data);
            return true;
        } catch (Throwable throwable) {
            LOG.error("Create error", throwable);
            return false;
        }
    }

    @Override
    public int delete(F filter) {
        try {
            return sqlSession.delete(deleteStatement, filter);
        } catch (Throwable throwable) {
            LOG.error("Delete error", throwable);
            return -1;
        }
    }

    @Override
    public int update(F filter, T data) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("filter", filter);
            param.put("data", data);
            return sqlSession.update(updateStatement, param);
        } catch (Throwable throwable) {
            LOG.error("Update error", throwable);
            return -1;
        }
    }

    @Override
    public <E extends T> List<E> list(F filter) {
        try {
            return sqlSession.selectList(listStatement, filter);
        } catch (Throwable throwable) {
            LOG.error("List error", throwable);
            return null;
        }
    }
}
