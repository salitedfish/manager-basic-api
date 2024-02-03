package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysBusinessAuth;

import java.util.List;

public interface ISysBusinessAuthSubAdminService {

    /**
     * 根据业务权限信息查询业务权限列表
     */
    public List<SysBusinessAuth> selectBusinessAuthList(SysBusinessAuth sbam);
}
