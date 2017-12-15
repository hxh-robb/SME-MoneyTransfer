package ft.repo;

import ft.spec.model.FundAccount;

/**
 * Fund account DAO
 */
public interface FundAccountDAO extends DAO<FundAccount, FundAccountDAO.Filter> {
    /**
     * FundAccount filter
     */
    class Filter extends DAO.Filter {
        // TODO
    }
}
