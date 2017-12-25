package ft.spec.model;

import java.util.Map;

/**
 * Fund account
 */
public class FundAccount extends SemiStructuredEntity {
    /**
     * Initialize fund account
     */
    public FundAccount() {
        super(true);
    }

    /**
     * Fund account name
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Fund account type
     * @see TransferAddon#value
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * Fund transfer option icon image URL
     */
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

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

    @Override
    public String toString() {
        return "FundAccount{" +
            super.toString(false) +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", icon='" + icon + '\'' +
            ", fields=" + unstructuredFields +
        '}';
    }

    /**
     * Adaptive setter for mybatis mapping
     * @param map
     */
    public void setFields(Map<String, Object> map) {
        set(map);
    }

    /**
     * Adaptive getter for mybatis mapping
     */
    public Map<String, Object> getFields() {
        return unstructuredFields;
    }
}
