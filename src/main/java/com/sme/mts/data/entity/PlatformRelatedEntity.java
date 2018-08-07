package com.sme.mts.data.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 平台相关实体
 */
public class PlatformRelatedEntity extends Entity {
    /**
     * 实体所属平台
     */
    protected String platform;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "{" +
            "id='" + id + '\'' +
            ", de=" + de +
            ", platform='" + platform + '\'' +
            ", hashcode='" + hashCode() + '\'' +
        '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PlatformRelatedEntity that = (PlatformRelatedEntity) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(platform, that.platform)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(platform)
                .toHashCode();
    }
}
