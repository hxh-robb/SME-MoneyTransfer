package ft.spec.service;

import java.io.Serializable;

/**
 * Service operation result
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
