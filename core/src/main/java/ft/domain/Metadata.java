package ft.domain;

/**
 * Metadata value object of fund-transfer service
 */
public class Metadata {
    /**
     * Get the supported FundAccount Types
     * @return supported FundAccount types
     */
    public static final Entry[] supportedFundAccountTypes(){
        return new Entry[]{
            // TODO
        };
    }

    /**
     * Metadata entry
     */
    public static final class Entry {
        public String name;
        public String desc;
        public String value;
    }
}
