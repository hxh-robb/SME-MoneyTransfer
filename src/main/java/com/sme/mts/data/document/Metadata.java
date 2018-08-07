package com.sme.mts.data.document;

import com.google.common.collect.ImmutableMap;
import com.sme.mts.data.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import org.mongodb.morphia.annotations.Entity;
import org.reflections.Reflections;

import java.util.Map;

/**
 * 元数据基类
 */
@Entity(value = "metadata", noClassnameStored = true)
public class Metadata extends Data {
    public static final Map<String,Class<? extends Metadata>> subclasses;
    static {
        ImmutableMap.Builder<String,Class<? extends Metadata>> builder =  ImmutableMap.builder();
        Reflections reflections = new Reflections(Metadata.class.getPackage().getName());
        reflections.getSubTypesOf(Metadata.class).forEach(c->builder.put(c.getSimpleName(),c));
        subclasses = builder.build();
    }

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
    protected final String catalog;

    public Metadata() {
        catalog = this.getClass().getSimpleName();
    }

    public Metadata(String catalog) {
        this.catalog = catalog;
    }

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
}
