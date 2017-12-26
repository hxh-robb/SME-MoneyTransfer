package ft.biz;

import ft.repo.FundAccountDAO;
import ft.spec.model.DepositOption;
import ft.spec.model.FundAccount;
import ft.spec.service.FundAccountService;

import java.util.List;

public class FundAccountBiz extends Biz<FundAccount, FundAccountDAO> implements FundAccountService {

    protected FundAccountBiz(FundAccountDAO dao) {
        super(dao); // Constructor injection
    }

    @Override
    public List<DepositOption> supportedDepositOption(String subject) {
        return null;
    }

    @Override
    protected FundAccountDAO.Filter createFilter() {
        return new FundAccountDAO.Filter();
    }

    @Override
    protected FundAccount createEntity() {
        return new FundAccount();
    }
}
