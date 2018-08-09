package com.sme.mts.data.document;

import org.mongodb.morphia.annotations.Entity;

/**
 * 转账插件
 */
@Entity(value = "metadata", noClassnameStored = true)
public class TransferAddon extends Addon {
    /**
     * 转账模式
     */
    protected Mode mode;

    /**
     * 入参表单schema
     */
    protected String schema;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * 转账模式(Addon calling sequence)
     */
    public enum Mode {
        /**
         * 银行转账收款
         */
        BANK_DEPOSIT,

        /**
         * 金融机构转账收款
         */
        INTERMEDIARY_DEPOSIT
    }
}
