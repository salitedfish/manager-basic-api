package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

public class SysDeptRole extends BaseEntity {
    private Long id;

    private String deptId;

    private Long roleId;

    private String roleName;

    private List<Long> roleIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysDeptRole{" +
                "id=" + id +
                ", deptId='" + deptId + '\'' +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleIds=" + roleIds +
                '}';
    }
}
