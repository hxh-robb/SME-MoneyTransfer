package com.sme.mts.data.repository.mariadb;

import com.sme.mts.data.Data;
import com.sme.mts.data.DataAccessObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * @param <D>
 * @param <F>
 */
public abstract class MybatisDAO<D extends Data, F extends DataAccessObject.Filter> implements DataAccessObject<D,F> {
    protected final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    public SqlSession sqlSession;

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
        this.namespace = namespace;

        String statementPattern = "{0}.{1}";
        this.createStatement = MessageFormat.format(statementPattern, this.namespace, "create");
        this.deleteStatement = MessageFormat.format(statementPattern, this.namespace, "delete");
        this.updateStatement = MessageFormat.format(statementPattern, this.namespace, "update");
        this.listStatement = MessageFormat.format(statementPattern, this.namespace, "list");
    }

    @Override
    public boolean create(D data) {
        try {
            sqlSession.insert(createStatement, data);
            return true;
        } catch (Throwable throwable) {
            logger.error("Create error", throwable);
            return false;
        }
    }

    @Override
    public int update(F filter, D data) {
        try {
            Map<String, Object> param = new HashMap<>();
            param.put("filter", filter);
            param.put("data", data);
            return sqlSession.update(updateStatement, param);
        } catch (Throwable throwable) {
            logger.error("Update error", throwable);
            return -1;
        }
    }

    @Override
    public int delete(F filter) {
        try {
            return sqlSession.delete(deleteStatement, filter);
        } catch (Throwable throwable) {
            logger.error("Delete error", throwable);
            return -1;
        }
    }

    @Override
    public <T extends D> List<T> list(F filter) {
        try {
            return sqlSession.selectList(listStatement, filter);
        } catch (Throwable throwable) {
            logger.error("List error", throwable);
            return null;
        }
    }
}
