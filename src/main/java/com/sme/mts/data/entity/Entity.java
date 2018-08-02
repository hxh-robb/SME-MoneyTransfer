package com.sme.mts.data.entity;

import com.sme.mts.data.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Date;

/**
 * 实体对象基类
 */
public abstract class Entity extends Data {
    /**
     * 创建时间
     */
    protected Date ct;

    /**
     * 最近更新时间
     */
    protected Date ts;

    /**
     * 创建人
     */
    protected String co;

    /**
     * 最近更新修改人
     */
    protected String uo;

    public Date getCt() {
        return ct;
    }

    public void setCt(Date ct) {
        this.ct = ct;
    }

    public Date getTs() {
        return ts;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getUo() {
        return uo;
    }

    public void setUo(String uo) {
        this.uo = uo;
    }
}
