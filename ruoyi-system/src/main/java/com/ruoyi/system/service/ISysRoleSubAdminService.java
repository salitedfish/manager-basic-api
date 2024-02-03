package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.SysPost;

import java.util.List;

public interface ISysRoleSubAdminService {

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(SysRole role);

    /**
     * 查询子管理员可查询的所有角色
     * @return
     */
    public List<SysRole> selectRoleAll();

    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    public List<SysRole> selectRolesByUserId(String userId);
}
