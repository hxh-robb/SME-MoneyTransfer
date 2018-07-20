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
