package com.ruoyi.common.enums;

public enum BusinessAuthOrgType {
    DEPT("0"), USER("1"), POST("2");

    private String value;

    private BusinessAuthOrgType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
