package com.sme.mts.service;

import com.sme.mts.data.entity.TransferTask;

/**
 * 转账任务服务接口
 */
public interface TransferTaskService extends TransactionalService {
    /**
     * 添加转账任务
     * @param amount 转账金额
     */
    TransferTask add(Double amount);
}
