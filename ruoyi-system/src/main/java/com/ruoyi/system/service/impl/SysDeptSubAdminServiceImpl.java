package com.ruoyi.system.service.impl;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.system.domain.SysDeptRole;
import com.ruoyi.system.domain.SysSubAdminDept;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysDeptSubAdminMapper;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysDeptSubAdminService;
import com.ruoyi.system.service.ISysSubAdminService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysDeptSubAdminServiceImpl implements ISysDeptSubAdminService {

    @Autowired
    private SysDeptSubAdminMapper deptSubAdminMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    @Autowired
    private ISysSubAdminService subAdminService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    public List<SysDept> selectDeptSubAdminList(SysDept dept)
    {
        String userId = SecurityUtils.getLoginUser().getUser().getUserId();
        return deptSubAdminMapper.selectDeptSubAdminList(dept, userId);
    }

    /**
     * 查询部门树结构信息
     *
     * @param dept 部门信息
     * @return 部门树信息集合
     */
    public List<TreeSelect> selectDeptTreeList(SysDept dept) {
        List<SysDept> depts = selectDeptSubAdminList(dept);
        return deptService.buildDeptTreeSelect(depts);
    };

    /**
     * 获取包含人员的部门树并且符合前端下拉选择所需要的
     * @param dept
     * @return
     */
    @Override
    public List<TreeSelect> selectDeptTreeListWithUser(SysDept dept) {
        // 获取部门列表，并构建部门树
        List<SysDept> depts = selectDeptSubAdminList(dept);
        List<SysDept> deptTrees = deptService.buildDeptTree(depts);

        // 获取人员列表，并构建包含人员和部门的选择树
        List<String> ids = depts.stream().map((item -> item.getDeptId())).collect(Collectors.toList());
        List<SysUser> users = userService.selectUserListByDeptIds(ids);
        List<TreeSelect> deptTreesWithUser = deptTrees.stream().map(d -> new TreeSelect(d, users)).collect(Collectors.toList());
        return deptTreesWithUser;
    };

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDeptSubAdmin(SysDept dept)
    {
        SysDept info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为正常状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus()))
        {
            throw new ServiceException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        dept.setDeptId(IdUtils.shortUUID());

        // 子管理员新增部门后，需要和子管理员建立关系
        String userId = SecurityUtils.getLoginUser().getUser().getUserId();
        SysSubAdminDept ssad = new SysSubAdminDept();
        List deptIds = new ArrayList();
        deptIds.add(dept.getDeptId());
        ssad.setUserId(userId);
        ssad.setDeptIds(deptIds);
        subAdminService.insertSubAdminDept(ssad);

        return deptMapper.insertDept(dept);
    }

    /**
     * 通过部门查询关联的角色列表
     * @param sdr
     * @return
     */
    public List<SysRole> selectRoleListByDept(SysDeptRole sdr) {
        String userId = SecurityUtils.getLoginUser().getUser().getUserId();
        return deptSubAdminMapper.selectRoleListByDept(sdr, userId);
    };

}
