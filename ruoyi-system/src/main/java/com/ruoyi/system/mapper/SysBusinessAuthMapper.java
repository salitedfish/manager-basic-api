package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.List;


public interface SysBusinessAuthMapper {

    /**
     * 查询业务权限列表
     * @param sbam
     * @return
     */
    public List<SysBusinessAuth> selectBusinessAuthList(SysBusinessAuth sbam);

    /**
     * 新增业务权限列表
     */
    public int insertBusinessAuth(List<SysBusinessAuth> sysBusinessAuthList);

    /**
     * 删除业务授权列表
     */
    public int deleteBusinessAuth(List<Long> businessAuthIds);

}
