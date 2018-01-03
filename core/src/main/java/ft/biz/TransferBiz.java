package ft.biz;

import ft.addon.Coordinator;
import ft.addon.TransferAddonConstant;
import ft.repo.FundAccountDAO;
import ft.spec.model.DepositSlip;
import ft.spec.model.FundAccount;
import ft.spec.model.TransferAddon;
import ft.spec.service.TransferService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fund transfer service implementation
 */
@Service
public class TransferBiz extends Biz implements TransferService {
    private FundAccountDAO fundAccountDAO;
    private Coordinator coordinator;

    public TransferBiz(FundAccountDAO fundAccountDAO, Coordinator coordinator) {
        this.fundAccountDAO = fundAccountDAO;
        this.coordinator = coordinator;
    }

    @Override
    public DepositSlip generateDepositSlip(String subject, String depositOption, Double amount) {
        // Result result = new Result();
        processSubject(subject);

        if(null == depositOption)
            return null;

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
        TransferAddon.Mode mode = account.getAddon().getMode();
        if( TransferAddon.Mode.INTERMEDIARY_DEPOSIT.equals(mode) ) {
            // TODO : register notification on program startup
        }

        /* TODO : Generate deposit slip, Script + amount + fundAccount.fields = DepositSlip */
        Map<String,Object> param = new HashMap<>();
        param.put(TransferAddonConstant.ParamKeys.ACCOUNT, account);
        param.put(TransferAddonConstant.ParamKeys.AMOUNT, amount);
        return coordinator.execute(account.getAddon().getId(), param);
    }
}
