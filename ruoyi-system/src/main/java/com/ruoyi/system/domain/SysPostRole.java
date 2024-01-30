package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

public class SysPostRole extends BaseEntity {
    private Long id;

    private String postId;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "SysPostRole{" +
                "id=" + id +
                ", postId='" + postId + '\'' +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                ", roleIds=" + roleIds +
                '}';
    }
}
