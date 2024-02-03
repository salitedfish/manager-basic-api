package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysBusiness;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.*;

import java.util.List;

/**
 * 分级管理数据层
 */
public interface SysSubAdminMapper {

    /**
     * 子管理员部门相关的增删查
     */
    public List<SysDept> selectSubAdminDept(SysSubAdminDept subAdminDept);

    public int insertSubAdminDept(List<SysSubAdminDept> subAdminDept);

    public int deleteSubAdminDept(List<SysSubAdminDept> subAdminDept);

    /**
     * 子管理员岗位相关的增删查
     */
    public List<SysPost> selectSubAdminPost(SysSubAdminPost subAdminPost);

    public int insertSubAdminPost(List<SysSubAdminPost> subAdminPost);

    public int deleteSubAdminPost(List<SysSubAdminPost> subAdminPost);

    /**
     * 子管理员角色相关的增删查
     */
    public List<SysRole> selectSubAdminRole(SysSubAdminRole subAdminRole);

    public int insertSubAdminRole(List<SysSubAdminRole> subAdminRole);

    public int deleteSubAdminRole(List<SysSubAdminRole> subAdminRole);

    /**
     * 子管理员业务相关的增删查
     */
    public List<SysBusiness> selectSubAdminBusiness(SysSubAdminBusiness subAdminBusiness);

    public int insertSubAdminBusiness(List<SysSubAdminBusiness> subAdminBusiness);

    public int deleteSubAdminBusiness(List<SysSubAdminBusiness> subAdminBusiness);
}
