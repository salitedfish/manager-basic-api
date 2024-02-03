package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

public class SysSubAdminRole extends BaseEntity {
    private String userId;

    private Long roleId;

    private List<Long> roleIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
