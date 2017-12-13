package ft.spec.model;

/**
 * Deposit addon
 */
public class DepositAddon extends Metadata {
    /**
     * <pre>
     * Constructor of Addon
     * </pre>
     */
    public DepositAddon() {
        super(CATALOG.FUND_ACCOUNT_TYPE); // Specify the metadata catalog
    }

    /**
     * <pre>
     * Deposit mode
     * This will define how addon participates in the deposit business process
     * </pre>
     */
    private Mode mode;

    /**
     * <pre>
     * Addon type
     * </pre>
     */
    private Type type;

    /**
     * <pre>
     * Fund account fields specification
     * </pre>
     */
    private String spec;

    /**
     * <pre>
     * Addon content
     * </pre>
     */
    private String content;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Addon type
     */
    public enum Type {
        /**
         * When addon type = java, content will be class name
         */
        JAVA(0),

        /**
         * When addon type = python, content will be the python script content
         */
        PYTHON(1)

        ;

        /**
         * Value for data storage
         */
        private int value;

        Type(int value) {
            this.value = value;
        }

        /**
         * Get value for data storage
         * @return
         */
        public int getValue() {
            return value;
        }

        /**
         * Get DepositMode enum object by value
         * @param value Value for data storage
         * @return DepositMode enum object
         */
        public static Type of(int value){
            for (Type type:values())
                if( type.value == value )
                    return type;
            return null;
        }
    }

    /**
     * Deposit mode enum
     */
    public enum Mode {
        /**
         * <pre>
         * Traditional deposit slip that contain bank account information
         * </pre>
         */
        BANK(0),

        /**
         * <pre>
         * Deposit slip represented as a HTML form which will auto-submit to third-party financial intermediaries
         * </pre>
         */
        INTERMEDIARY(1)

        ;

        /**
         * Value for data storage
         */
        private int value;

        Mode(int value) {
            this.value = value;
        }

        /**
         * Get value for data storage
         * @return
         */
        public int getValue() {
            return value;
        }

        /**
         * Get DepositMode enum object by value
         * @param value Value for data storage
         * @return DepositMode enum object
         */
        public static Mode of(int value){
            for (Mode mode:values())
                if( mode.value == value )
                    return mode;
            return null;
        }
    }

    @Override
    public String toString() {
        return "DepositAddon{" +
            "mode=" + mode +
            ", type=" + type +
            ", content=" + content +
            ", catalog=" + catalog +
            ", name=" + name +
            ", description=" + description +
            ", value=" + value +
            ", enabled=" + enabled +
        '}';
    }
}
