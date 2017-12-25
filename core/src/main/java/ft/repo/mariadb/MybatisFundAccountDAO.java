package ft.repo.mariadb;

import ft.repo.FundAccountDAO;
import ft.spec.model.FundAccount;
import org.springframework.stereotype.Repository;

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
}
