package com.ruoyi.common.enums;

/**
 * 选择树的节点类型
 */
public enum SelectTreeNodeType {
    DEPT("0"), USER("1");

    private String value;

    private SelectTreeNodeType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
