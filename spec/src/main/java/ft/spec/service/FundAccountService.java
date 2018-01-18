package ft.spec.service;

import ft.spec.model.DepositOption;
import ft.spec.model.FundAccount;

import java.util.List;

/**
 * Fund account service
 */
public interface FundAccountService extends EntityService<FundAccount> {
    /**
     * List supported deposit options
     * @param subject
     * @return
     */
    List<DepositOption> supportedDepositOption(String subject);

    /**
     * Generate fund account json form schema
     * @param subject
     * @return
     */
    String jsonFormSchema(String subject);

    /**
     * Fund account service result code
     */
    interface Code extends Result.Code {
        // TODO
    }
}
