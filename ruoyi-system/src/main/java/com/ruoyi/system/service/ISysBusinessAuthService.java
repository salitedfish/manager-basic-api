package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.List;

public interface ISysBusinessAuthService {

    /**
     * 根据业务权限信息查询业务权限列表
     */
    public List<SysBusinessAuth> selectBusinessAuthList(SysBusinessAuth sbam);

    /**
     * 新增业务权限
     */
    public int insertBusinessAuth(List<SysBusinessAuth> sysBusinessAuthList);

    /**
     * 删除业务授权列表
     */
    public int deleteBusinessAuth(List<Long> businessAuthIds);

}
