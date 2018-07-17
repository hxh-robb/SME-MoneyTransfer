package com.sme.mts.data.document;

/**
 * 系统插件
 */
public class Addon extends Metadata {
    /**
     * 插件类型
     */
    protected Type type;

    /**
     * 插件内容
     */
    protected String content;

    /**
     * 插件类型
     */
    public enum Type {
        JAVA_CLASS, PYTHON
    }
}