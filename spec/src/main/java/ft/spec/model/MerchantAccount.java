package ft.spec.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Merchant account for intermediary
 * (Semi-structured data)
 */
public class MerchantAccount extends FundAccount {
    /**
     * dynamic fields
     */
    private final Map<String, Object> fields = new HashMap<>();

    /**
     * set values
     * @param json
     */
    public void set(String json) { }

    /**
     * set value
     * @param field
     * @param value
     */
    public void set(String field, Object value) {
        if( null == value ) {
            fields.remove(field);
        }
        fields.put(field, value);
    }
}
