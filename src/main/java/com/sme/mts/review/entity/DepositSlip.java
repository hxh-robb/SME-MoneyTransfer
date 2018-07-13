package com.sme.mts.review.entity;

import java.io.Serializable;

/**
 * Deposit slip
 */
public class DepositSlip implements Serializable {
    public interface MIME {
        String JSON = "application/json; charset=UTF-8";
        String HTML = "text/html; charset=UTF-8";
    }

    public String mime;
    public String content;

    @Override
    public String toString() {
        return "DepositSlip{" +
            "mime='" + mime + '\'' +
            ", content='" + content + '\'' +
        '}';
    }
}
