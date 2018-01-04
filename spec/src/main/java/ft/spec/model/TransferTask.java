package ft.spec.model;

/**
 * Fund transfer task
 */
public class TransferTask extends Entity {
    public TransferTask(){
        super(true);
        // ref = id.substring(0,4);
    }

    /**
     * Beneficiary(player user id).
     * If it's a deposit task(cash in), then the benefit is credit adding;
     * If it's a withdrawal task(cash out), then the benefit is money transfer;
     */
    private String beneficiary;

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
    public enum Status {
        // TODO
    }
}
