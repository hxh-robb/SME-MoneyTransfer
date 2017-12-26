package ft.repo.mariadb;

import ft.repo.FundAccountDAO;
import ft.spec.model.DepositOption;
import ft.spec.model.FundAccount;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FundAccountDAO implementation for MariaDB
 */
@Repository
public class MybatisFundAccountDAO extends MybatisDAO<FundAccount, FundAccountDAO.Filter> implements FundAccountDAO {

    /**
     * Constructor
     */
    public MybatisFundAccountDAO() {
        super("FundAccountMapper"); // Specify mybatis mapper namespace
    }

    @Override
    public List<DepositOption> listDepositOptions(FundAccountDAO.Filter filter) {
        try {
            if( null == filter ) filter = new FundAccountDAO.Filter();
            filter.info = true;
            return sqlSession.selectList(listStatement, filter);
        } catch (Throwable throwable) {
            LOG.error("List error", throwable);
            return null;
        }
    }
}
