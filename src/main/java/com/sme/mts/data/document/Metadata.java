package com.sme.mts.data.document;

import com.sme.mts.data.Data;
import org.mongodb.morphia.annotations.Entity;

/**
 * 元数据基类
 */
@Entity(value = "metadata", noClassnameStored = true)
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
    protected String catalog = this.getClass().getSimpleName();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}
