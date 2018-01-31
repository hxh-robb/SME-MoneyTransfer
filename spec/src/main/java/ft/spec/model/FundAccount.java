package ft.spec.model;

import java.util.Map;

/**
 * Fund account
 */
public class FundAccount extends SemiStructuredEntity {

    /**
     * Type
     * @see TransferAddon#value
     */
    private String type;

    /**
     * Addon(Filtered by {@link TransferAddon#value})
     */
    private transient TransferAddon addon;

    /**
     * Processing fund upper bound
     */
    private Double upperBound;

    /**
     * Processing fund lower bound
     */
    private Double lowerBound;

    // TODO:additional business logical fields
//    /**
//     * Transfer fee
//     */
//    protected Double fee;
//
//    public Double getFee() {
//        return fee;
//    }
//
//    public void setFee(Double fee) {
//        this.fee = fee;
//    }

    /**
     * Fund account constructor
     */
    public FundAccount() {
        super(true, new DepositOption());
    }

    /* getters/setters */

    public final DepositOption getInfo() {
        return (DepositOption)struct;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public TransferAddon getAddon() {
        return addon;
    }

    public void setAddon(TransferAddon addon) {
        this.addon = addon;
    }

    public Double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Double upperBound) {
        this.upperBound = upperBound;
    }

    public Double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Double lowerBound) {
        this.lowerBound = lowerBound;
    }

    @Override
    public String toString() {
        return "FundAccount{" +
            super.toString(false) +
            ", " + getInfo().toString(false) +
            ", type='" + type + '\'' +
            ", addon='" + addon + '\'' +
            ", fields=" + unstructuredFields +
        '}';
    }

    /**
     * Adaptive setter for mybatis mapping
     * @param map
     */
    public final void setFields(Map<String, Object> map) {
        set(map);
    }

    /**
     * Adaptive getter for mybatis mapping
     */
    public final Map<String, Object> getFields() {
        return unstructuredFields;
    }

    @Override
    public void set(String field, Object value) {
        if( "".equals(value) ) {
            value = null;
        }
        super.set(field, value);
    }
}
