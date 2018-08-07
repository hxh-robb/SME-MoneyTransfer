package com.sme.mts.data.document;

import com.sme.mts.data.Data;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

import java.util.HashMap;
import java.util.Map;

/**
 * 资金账号(非结构化部分)
 * 关联至{@link com.sme.mts.data.entity.FundAccount}
 */
@Entity(value = "fund_account", noClassnameStored = true)
public class FundAccount extends Data {
    /**
     * 可以处理该资金账号的转账插件,{@link TransferAddon#id};
     * TODO - TransferAddon one-to-one mapping
     */
    @Reference
    protected TransferAddon addon;

    /**
     * 资金账户动态字段(各金融机构资金账号的字段定义存在极大差异)
     */
    // @DynamicFields // field name conflict issue
    protected final HashMap<String, Object> fields = new HashMap<String, Object>(){
        @Override
        public Object put(String key, Object value) {
            if( null == value ) return this.remove(key);
            return super.put(key, value);
        }
    };

    public Map<String, Object> getFields() {
        return fields;
    }

    public TransferAddon getAddon() {
        return addon;
    }

    public void setAddon(TransferAddon addon) {
        this.addon = addon;
    }
}
