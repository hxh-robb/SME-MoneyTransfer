package ft.spec.service;

import ft.spec.model.DepositSlip;

import java.io.Serializable;

/**
 * Fund transfer service
 */
public interface TransferService {
    /**
     * Generate deposit slip
     * @param subject applicant of the deposit slip
     * @param depositOption
     * @param amount
     * @return
     */
    DepositSlip generateDepositSlip(
        String subject,
        String depositOption,
        Double amount
    );

    /**
     * Transfer service result code
     */
    interface Code extends Result.Code {
        // TODO
    }
}
