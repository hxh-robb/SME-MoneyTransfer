package ft.spec.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Fund account
 */
public class FundAccount extends Entity {
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
     * @see ft.spec.model.Metadata.CATALOG#FUND_ACCOUNT_TYPE
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
     * dynamic fields
     */
    private final Map<String, Object> fields = new HashMap<>();

    /**
     * set data
     * @param data
     */
    public void set(Map<String, Object> data) {
        fields.clear();
        fields.putAll(data);
    }

    /**
     * set value
     * @param field
     * @param value
     */
    public void set(String field, Object value) {
        if( "type".equals(field) ) {
            setType(String.valueOf(value));
            return;
        }

        if( null == value ) {
            fields.remove(field);
        }
        fields.put(field, value);
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
}
