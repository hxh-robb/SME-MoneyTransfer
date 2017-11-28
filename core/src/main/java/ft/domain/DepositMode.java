package ft.domain;

/**
 * Deposit mode enum
 */
public enum DepositMode {
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

    DepositMode(int value) {
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
    public static DepositMode of(int value){
        for (DepositMode mode:values())
            if( mode.value == value )
                return mode;
        return null;
    }
}
