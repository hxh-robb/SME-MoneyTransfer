package com.sme.mts.data.entity;

import java.util.Date;

/**
 * 实体对象基类
 */
public abstract class Base {
    /**
     * 数据UUID
     */
    protected String id;

    /**
     * 创建时间
     */
    protected Date ct;

    /**
     * 最近更新时间
     */
    protected Date ts;

    /**
     * 是否被软删除
     */
    protected Boolean de;

    /**
     * 创建人
     */
    protected String co;

    /**
     * 最近更新修改人
     */
    protected String uo;
}
