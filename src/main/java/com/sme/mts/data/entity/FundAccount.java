package com.sme.mts.data.entity;

/**
 * 资金账号(结构化部分)
 */
public class FundAccount extends PlatformRelatedEntity {
    /**
     * 类型:0-收款任务;1-出款任务;
     */
    protected Integer type;

    /**
     * 名称
     */
    protected String name;

    /**
     * 展示标题
     */
    protected String title;

    /**
     * 图标地址
     */
    protected String icon;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
