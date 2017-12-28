package ft.biz;

import ft.repo.FundAccountDAO;
import ft.spec.model.DepositSlip;
import ft.spec.model.FundAccount;
import ft.spec.service.TransferService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Fund transfer service implementation
 */
@Service
public class TransferBiz extends Biz implements TransferService {
    private FundAccountDAO fundAccountDAO;

    public TransferBiz(FundAccountDAO fundAccountDAO) {
        this.fundAccountDAO = fundAccountDAO;
    }

    @Override
    public DepositSlip generateDepositSlip(String subject, String depositOption, Double amount) {
        // Result result = new Result();
        processSubject(subject);

        FundAccountDAO.Filter filter = new FundAccountDAO.Filter();
        filter.id = depositOption;
        filter.addon = true;
        filter.de = false;
        List<FundAccount> accounts = fundAccountDAO.list(filter);

        if( null == accounts || accounts.isEmpty())
            return null; // Invalid fund account

        FundAccount account = accounts.get(0);
        if( null == account.getAddon() )
            return null; // Invalid addon

        // TODO : Create task for following deposit process

        account.getAddon();
        DepositSlip slip = new DepositSlip();
        /* TODO : Generate deposit slip
        Script + amount + fundAccount fields = DepositSlip
         */
        return slip;
    }
}
