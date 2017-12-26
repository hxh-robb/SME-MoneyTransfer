package ft.spec.service;

import ft.spec.model.DepositOption;
import ft.spec.model.FundAccount;

import java.io.Serializable;
import java.util.List;

/**
 * TODO
 */
public interface FundAccountService extends Service<FundAccount> {

    /**
     * Create new fund account
     * @param subject The operator
     * @param account FundAccount
     * @return
     */
    Result create(String subject, FundAccount account);

    /**
     * Update specific fund account
     * @param subject The operator
     * @param metadata Metadata
     * @return
     */
    Result update(String subject, FundAccount metadata);

    /**
     * Delete specific fund account
     * @param subject The operator
     * @param id  ID of the fund account
     * @return
     */
    Result delete(String subject, String id);

    /**
     * List supported deposit options
     * @param subject
     * @return
     */
    List<DepositOption> supportedDepositOption(String subject);

    /**
     * Fund account service result code
     */
    interface Code extends Result.Code {
        // TODO
    }
}
