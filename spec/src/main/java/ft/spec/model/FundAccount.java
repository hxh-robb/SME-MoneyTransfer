package ft.spec.model;

/**
 * Fund account
 */
public class FundAccount {
    /**
     * Fund account type
     * @see ft.spec.model.Metadata.CATALOG#FUND_ACCOUNT_TYPE
     * @see DepositAddon#value
     */
    protected String type;

    /**
     * Transfer fee
     */
    protected Double fee;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }
}
