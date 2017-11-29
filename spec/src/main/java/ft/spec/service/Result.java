package ft.spec.service;

/**
 * Service operation result
 */
public class Result {

    /**
     * Result code
     */
    private int code;

    /**
     * Result message
     */
    private String msg;

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
        int SUCCESS = 0;
        int UNKNOW = 0xFFFF;
    }
}
