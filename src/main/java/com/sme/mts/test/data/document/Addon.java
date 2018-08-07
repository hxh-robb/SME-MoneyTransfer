package com.sme.mts.test.data.document;

import org.mongodb.morphia.annotations.Entity;

/**
 * 系统插件
 */
@Entity(value = "metadata", noClassnameStored = true)
public class Addon extends Metadata {
    /**
     * 插件类型
     */
    protected Type type;

    /**
     * 插件内容
     */
    protected String content;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 插件类型
     */
    public enum Type {
        JAVA_CLASS, PYTHON
    }
}