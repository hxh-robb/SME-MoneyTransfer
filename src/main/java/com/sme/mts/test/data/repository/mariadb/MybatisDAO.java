package com.sme.mts.test.data.repository.mariadb;

import com.sme.mts.test.data.Data;
import com.sme.mts.test.data.DataAccessObject;
import com.sme.mts.test.data.LoggingDAO;
import org.apache.ibatis.session.SqlSession;
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
public abstract class MybatisDAO<D extends Data, F extends DataAccessObject.Filter> extends LoggingDAO<D,F> implements DataAccessObject<D,F> {
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
    protected void doCreate(D data) {
        sqlSession.insert(createStatement, data);
    }

    @Override
    protected int doUpdate(F filter, D data) {
        Map<String, Object> param = new HashMap<>();
        param.put("filter", filter);
        param.put("data", data);
        return sqlSession.update(updateStatement, param);
    }

    @Override
    protected int doDelete(F filter) {
        return sqlSession.delete(deleteStatement, filter);
    }

    @Override
    protected List<D> doList(F filter) {
        return sqlSession.selectList(listStatement, filter);
    }
}
