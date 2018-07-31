package com.sme.mts.data.document;

import com.sme.mts.data.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资金账号(非结构化部分)
 * 关联至{@link com.sme.mts.data.entity.FundAccount}
 */
@Entity(value = "fund_account", noClassnameStored = true)
public class FundAccount extends Data {
    /**
     * 可以处理该资金账号的转账插件,{@link TransferAddon#id}
     */
    protected String addon;

    /**
     * 资金账户动态字段(各金融机构资金账号的字段定义存在极大差异)
     */
    protected final Map<String, Object> fields = new ConcurrentHashMap<>();

    public Map<String, Object> getFields() {
        return fields;
    }

    public String getAddon() {

        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }
}
