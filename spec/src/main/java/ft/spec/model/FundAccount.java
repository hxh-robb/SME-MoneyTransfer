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
    private TransferAddon addon;

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

//    /**
//     * Fund account name
//     */
//    private String name;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    /**
//     * Fund account type
//     * @see TransferAddon#value
//     */
//    private String type;
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    /**
//     * Fund transfer option icon image URL
//     */
//    private String icon;
//
//    public String getIcon() {
//        return icon;
//    }
//
//    public void setIcon(String icon) {
//        this.icon = icon;
//    }

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
}
