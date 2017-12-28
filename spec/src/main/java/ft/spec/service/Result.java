package ft.spec.service;

import java.io.Serializable;

/**
 * EntityService operation result
 */
public final class Result implements Serializable {
    /**
     * Result code
     * @see Code
     */
    private int code;

    /**
     * Result message
     */
    private String msg;

    public Result() {
        code = Code.UNKNOWN;
    }

    public Result(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public Result setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Common result code
     */
    public interface Code {
        /**
         * Success result code
         */
        int SUCCESS = 0;

        /**
         * Given entity has invalid properties
         */
        int INVALID_ENTITY = 1;

        /**
         * Given entity is duplicated
         */
        int DUPLICATED_ENTITY = 2;

        /**
         * Given parameters are invalid
         */
        int INVALID_PARAMETERS = 3;

        /**
         * Unknown error result code
         */
        int UNKNOWN = 0xFFFF;
    }

    @Override
    public String toString() {
        return "Result{" +
            "code=" + code +
            ", msg='" + msg + '\'' +
        '}';
    }
}
