package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.SysRoleMapper;
import com.ruoyi.system.mapper.SysRoleSubAdminMapper;
import com.ruoyi.system.service.ISysRoleSubAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleSubAdminServiceImpl implements ISysRoleSubAdminService {

    @Autowired
    private SysRoleSubAdminMapper roleSubAdminMapper;

    @Autowired
    private SysRoleMapper roleMapper;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    public List<SysRole> selectRoleList(SysRole role) {
        String userId = SecurityUtils.getLoginUser().getUser().getUserId();
        return roleSubAdminMapper.selectRoleList(role, userId);
    };

    /**
     * 查询子管理员可查询的所有角色
     * @return
     */
    @Override
    public List<SysRole> selectRoleAll() {
        String userId = SecurityUtils.getLoginUser().getUser().getUserId();
        return roleSubAdminMapper.selectRoleAll(userId);
    };

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(String userId)
    {
        List<SysRole> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        List<SysRole> roles = selectRoleAll();
        for (SysRole role : roles)
        {
            for (SysRole userRole : userRoles)
            {
                if (role.getRoleId().longValue() == userRole.getRoleId().longValue())
                {
                    role.setFlag(true);
                    break;
                }
            }
        }
        return roles;
    }
}
