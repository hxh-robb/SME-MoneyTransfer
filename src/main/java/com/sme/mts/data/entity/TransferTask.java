package com.sme.mts.data.entity;

/**
 * 转账任务
 */
public class TransferTask extends PlatformRelatedEntity {
    /**
     * 任务类型:0-收款任务;1-出款任务;
     */
    protected Integer type;

    /**
     * 转账任务的受益人
     */
    protected String beneficiary;

    /**
     * 转账金额
     */
    protected Double amount;

    /**
     * 处理阶段状态(处理中/结束)
     */
    protected String status;

    /**
     * 处理结果(成功/失败)
     */
    protected String state;

    /**
     * 处理失败是否重试
     */
    protected Boolean retry;

    /**
     * 失败重试流程状态,{@link TransferTask#status}
     */
    protected String fallback;

    /**
     * 与外部系统对接关联的唯一标识
     */
    protected String ref;

    /**
     * 负责处理该笔转账任务的资金账号,{@link FundAccount#id}
     */
    protected String fundAccount;
}
