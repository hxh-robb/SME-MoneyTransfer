package com.sme.mts.data.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public interface Type {
        int DEPOSIT_ACCOUNT = 0;
        int WITHDRWAL_ACCOUNT = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        FundAccount that = (FundAccount) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(type, that.type)
                .append(name, that.name)
                .append(title, that.title)
                .append(icon, that.icon)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(type)
                .append(name)
                .append(title)
                .append(icon)
                .toHashCode();
    }
}
