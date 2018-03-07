package ft.addon;

import com.fasterxml.jackson.databind.ObjectMapper;
import ft.spec.model.DepositSlip;
import ft.spec.model.FundAccount;
import ft.spec.model.TransferTask;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Traditional bank deposit slip generator
 */
class BankDepositSlipGenerator implements Addon,TransferAddonConstant {

    @Override
    public <T> T execute(Map<String, Object> parameters) {
        return (T)execute(Functions.GENERATE,parameters);
    }

    @Override
    public <T> T execute(String func, Map<String, Object> parameters) {
        Parameters param = Parameters.instance(parameters);
        if( Functions.GENERATE.equalsIgnoreCase(func) )
            return (T)generate(param.account,param.task);
        throw new UnsupportedOperationException(MessageFormat.format("Unsupported function:[{0}]", func));
    }

    /**
     * Generate slip(Default execution)
     * @param account
     * @param task
     * @return
     */
    private DepositSlip generate(FundAccount account, TransferTask task) {
        try {
            Slip slip = new Slip();
            slip.account = account.get(Fields.ACCOUNT);
            slip.holder = account.get(Fields.HOLDER);
            slip.bank = account.get(Fields.BANK);
            slip.branch = account.get(Fields.BRANCH);
            slip.amount = task.getAmount();
            slip.ps = task.getId().substring(0,4);

            // set dynamic parameter back to task object
            task.setRef(slip.ps);

            DepositSlip ds = new DepositSlip();
            ds.mime = DepositSlip.MIME.JSON;
            ds.content = new ObjectMapper().writeValueAsString(slip);
            return ds;
        } catch (Throwable throwable) {
            throw new RuntimeException("Cannot generate deposit slip", throwable);
        }
    }

    /**
     * Slip json skeleton
     */
    private static final class Slip {
        public String account, holder, bank, branch, ps;
        public Double amount;
    }

    /**
     * Keys for account dynamic column
     */
    private interface Fields {
        String ACCOUNT = "account";
        String HOLDER = "holder";
        String BANK = "bank";
        String BRANCH = "branch";
    }
}
