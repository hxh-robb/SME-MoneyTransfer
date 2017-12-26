package ft.repo;

import ft.spec.model.DepositOption;
import ft.spec.model.FundAccount;

import java.util.List;

/**
 * Fund account DAO
 */
public interface FundAccountDAO extends DAO<FundAccount, FundAccountDAO.Filter> {
    /**
     * Same as {@link FundAccountDAO#list(DAO.Filter)}. but map to {@link DepositOption}
     * @param filter
     * @return
     */
    List<DepositOption> listDepositOptions(Filter filter);

    /**
     * FundAccount filter
     */
    class Filter extends DAO.Filter {
        public String name;
        public String type;
        public Boolean info = false;
    }
}
