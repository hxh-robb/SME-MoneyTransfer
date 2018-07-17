package com.sme.mts.data.document;

/**
 * 转账插件
 */
public class TransferAddon extends Addon {
    /**
     * 转账模式
     */
    protected Mode mode;

    /**
     * 入参表单规格
     */
    protected String spec;

    /**
     * 转账模式
     */
    public enum Mode {
        /**
         * 银行转账
         */
        BANK_DEPOSIT,

        /**
         * 金融机构转账
         */
        INTERMEDIARY_DEPOSIT
    }
}
