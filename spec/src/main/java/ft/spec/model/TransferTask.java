package ft.spec.model;

/**
 * Fund transfer task
 */
public class TransferTask extends Entity {
    public TransferTask(Type type){
        super(true);
        this.type = type;
        // ref = id.substring(0,4);
    }

    private Type type;

    /**
     * Beneficiary(player user id).
     * If it's a deposit task(cash in), then the benefit is credit adding;
     * If it's a withdrawal task(cash out), then the benefit is money transfer;
     */
    private String beneficiary;

    /**
     * Source platform, which platform does this task belong to
     */
    private String platform;

    /**
     * Fund amount
     */
    private Double amount;

    /**
     * Task status
     */
    private Status status;

//    /**
//     * Fund account
//     */
//    private String accountId;

//    /**
//     * Fund account
//     */
//    private FundAccount account;

    /**
     * Reference flag:
     * Bank deposit - the postscript
     * Intermediary deposit - order id
     */
    private String ref;

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    /**
     * Status of transfer task
     */
    public enum Type {
        /**
         * User's deposit(cash in, user deposits money to platform)
         */
        DEPOSIT,

        /**
         * User's withdrawal(cash out, user withdraws money from platform)
         */
        WITHDRAWAL
    }

    /**
     * Status of transfer task
     */
    public enum Status {
        // TODO
    }
}
