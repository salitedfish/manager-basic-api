package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.SysBusiness;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.*;

import java.util.List;

/**
 * 分级管理
 */
public interface ISysSubAdminService {

    /**
     * 子管理员部门相关的增删查
     */
    public List<SysDept> selectSubAdminDept(SysSubAdminDept subAdminDept);

    public int insertSubAdminDept(SysSubAdminDept subAdminDept);

    public int deleteSubAdminDept(SysSubAdminDept subAdminDept);

    /**
     * 子管理员岗位相关的增删查
     */
    public List<SysPost> selectSubAdminPost(SysSubAdminPost subAdminPost);

    public int insertSubAdminPost(SysSubAdminPost subAdminPost);

    public int deleteSubAdminPost(SysSubAdminPost subAdminPost);

    /**
     * 子管理员角色相关的增删查
     */
    public List<SysRole> selectSubAdminRole(SysSubAdminRole subAdminRole);

    public int insertSubAdminRole(SysSubAdminRole subAdminRole);

    public int deleteSubAdminRole(SysSubAdminRole subAdminRole);

    /**
     * 子管理员业务相关的增删查
     */
    public List<SysBusiness> selectSubAdminBusiness(SysSubAdminBusiness subAdminBusiness);

    public int insertSubAdminBusiness(SysSubAdminBusiness subAdminBusiness);

    public int deleteSubAdminBusiness(SysSubAdminBusiness subAdminBusiness);
}
