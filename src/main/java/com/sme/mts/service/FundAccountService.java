package com.sme.mts.service;

import java.util.List;
import java.util.Map;

/**
 * 资金账号服务接口
 */
public interface FundAccountService extends TransactionalService {
    /**
     * 添加资金账号
     * @param account 资金账号
     */
    Map add(Map account);

    /**
     * 获取资金账号
     * @return 资金账号列表
     */
    List<Map> list();
}
