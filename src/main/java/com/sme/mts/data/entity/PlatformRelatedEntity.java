package com.sme.mts.data.entity;

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
}
