package ft.spec.model;

/**
 * TODO
 */
public class DepositSlip {
    public String mime;
    public String content;
    public String ref;

    @Override
    public String toString() {
        return "DepositSlip{" +
            "mime='" + mime + '\'' +
            ", content='" + content + '\'' +
            ", ref='" + ref + '\'' +
        '}';
    }
}
