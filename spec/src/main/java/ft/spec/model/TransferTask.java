package ft.spec.model;

/**
 * Fund transfer task
 */
public class TransferTask extends Entity {
    /**
     * Beneficiary(player user id)
     */
    private String beneficiary;

    /**
     * Fund amount
     */
    private String amount;

    /**
     * Task status
     */
    private String status;

    /**
     * Fund account
     */
    private FundAccount account;
}
