package com.sme.mts.review.entity;

import java.io.Serializable;
import java.text.MessageFormat;

/**
 * Deposit option
 */
public class DepositOption implements Serializable {
    /**
     * @see FundAccount#id
     */
    private String id;

    /**
     * Name
     */
    private String name;

    /**
     * Icon image URL
     */
    private String icon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return toString(true);
    }

    protected String toString(boolean complete) {
        String fields =
                (complete ? "id=" + id + ", " : "") +
                "name=" + name +
                ", icon=" + icon ;
        return complete ? MessageFormat.format("DepositOption'{'{0}'}'", fields) : fields;
    }
}
