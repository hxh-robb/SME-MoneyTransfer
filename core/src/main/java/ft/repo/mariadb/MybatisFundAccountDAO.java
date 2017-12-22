package ft.repo.mariadb;

import ft.repo.FundAccountDAO;
import ft.spec.model.FundAccount;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FundAccountDAO implementation for MariaDB
 */
@Repository
public class MybatisFundAccountDAO implements FundAccountDAO {
    private Logger LOG = LoggerFactory.getLogger(MybatisFundAccountDAO.class);

    @Autowired
    private SqlSession sqlSession;

    @Override
    public boolean create(FundAccount data) {
        sqlSession.insert("FundAccountMapper.create", data);
        return false;
    }

    @Override
    public int update(Filter filter, FundAccount data) {
        return 0;
    }

    @Override
    public int delete(Filter filter) {
        return 0;
    }

    @Override
    public <E extends FundAccount> List<E> list(Filter filter) {
        return null;
    }
}
