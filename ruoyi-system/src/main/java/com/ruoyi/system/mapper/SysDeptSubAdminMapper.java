package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.SysDeptRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDeptSubAdminMapper {

    /**
     * 查询部门管理数据 (分级管理)
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDept> selectDeptSubAdminList(@Param("dept") SysDept dept, @Param("userId") String userId);

    /**
     * 通过部门查询关联的角色列表
     * @param sdr
     * @return
     */
    public List<SysRole> selectRoleListByDept(@Param("deptRole") SysDeptRole sdr, @Param("userId") String userId);
}
