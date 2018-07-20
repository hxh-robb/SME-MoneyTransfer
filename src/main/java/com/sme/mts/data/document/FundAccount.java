package com.sme.mts.data.document;

import com.sme.mts.data.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资金账号(非结构化部分)
 * 关联至{@link com.sme.mts.data.entity.FundAccount}
 */
public class FundAccount extends Data {
    /**
     * 可以处理该资金账号的转账插件,{@link TransferAddon#id}
     */
    protected String addon;

    /**
     * 资金账户动态字段(各金融机构资金账号的字段定义存在极大差异)
     */
    protected Map<String, Object> fields;
}
