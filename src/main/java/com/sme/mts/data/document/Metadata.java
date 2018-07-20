package com.sme.mts.data.document;

import com.sme.mts.data.Data;

/**
 * 元数据基类
 */
public class Metadata extends Data {

    /**
     * 元数据名称
     */
    protected String name;

    /**
     * 元数据值
     */
    protected String value;

    /**
     * 元数据描述
     */
    protected String description;

    /**
     * 元数据分类
     */
    protected String catalog;
}
