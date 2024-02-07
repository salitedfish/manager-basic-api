package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.SysDeptRole;

import java.util.List;

public interface ISysDeptSubAdminService {

    /**
     * 查询部门管理数据 (分级管理)
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptSubAdminList(SysDept dept);

    /**
     * 查询部门管理全部数据 (分级管理)
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptSubAdminAllList(SysDept dept);


    /**
     * 查询部门树结构信息
     *
     * @param dept 部门信息
     * @return 部门树信息集合
     */
    public List<TreeSelect> selectDeptTreeList(SysDept dept);


    /**
     * 获取包含人员的部门树
     * @param dept
     * @return
     */
    public List<TreeSelect> selectDeptTreeListWithUser(SysDept dept);

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDeptSubAdmin(SysDept dept);

    /**
     * 通过部门查询关联的角色列表
     * @param sdr
     * @return
     */
    public List<SysRole> selectRoleListByDept(SysDeptRole sdr);

}
