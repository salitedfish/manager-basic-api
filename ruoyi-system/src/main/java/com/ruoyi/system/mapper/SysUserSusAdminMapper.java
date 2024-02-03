package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserSusAdminMapper {

    /**
     * 根据条件分页查询用户列表
     *
     * @param sysUser 用户信息
     * @return 用户信息集合信息
     */
    public List<SysUser> selectUserList(@Param("sysUser") SysUser sysUser, @Param("userId") String userId);
}
