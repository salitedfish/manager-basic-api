package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysBusiness;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.SysSubAdminMapper;
import com.ruoyi.system.service.ISysSubAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 分级管理
 */
@Service
public class SysSubAdminServiceImpl implements ISysSubAdminService {

    @Autowired
    private SysSubAdminMapper subAdminMapper;


    /**
     * 子管理员部门相关的增删查
     */
    @Override
    public List<SysDept> selectSubAdminDept(SysSubAdminDept subAdminDept) {
        return subAdminMapper.selectSubAdminDept(subAdminDept);
    };
    @Override
    public int insertSubAdminDept(SysSubAdminDept subAdminDept) {
        return subAdminMapper.insertSubAdminDept(genSysSubAdminDeptList(subAdminDept));

    };
    @Override
    public int deleteSubAdminDept(SysSubAdminDept subAdminDept) {
        return subAdminMapper.deleteSubAdminDept(genSysSubAdminDeptList(subAdminDept));
    };
    public List<SysSubAdminDept> genSysSubAdminDeptList (SysSubAdminDept subAdminDept) {
        List<SysSubAdminDept> list = new ArrayList<>();
        for(String item: subAdminDept.getDeptIds()) {
            SysSubAdminDept i = new SysSubAdminDept();
            i.setUserId(subAdminDept.getUserId());
            i.setDeptId(item);
            list.add(i);
        }
        return list;
    }

    /**
     * 子管理员岗位相关的增删查
     */
    public List<SysPost> selectSubAdminPost(SysSubAdminPost subAdminPost) {
        return subAdminMapper.selectSubAdminPost(subAdminPost);

    };
    public int insertSubAdminPost(SysSubAdminPost subAdminPost) {
        return subAdminMapper.insertSubAdminPost(genSysSubAdminPostList(subAdminPost));

    };
    public int deleteSubAdminPost(SysSubAdminPost subAdminPost) {
        return subAdminMapper.deleteSubAdminPost(genSysSubAdminPostList(subAdminPost));

    };
    public List<SysSubAdminPost> genSysSubAdminPostList (SysSubAdminPost subAdminPost) {
        List<SysSubAdminPost> list = new ArrayList<>();
        for(String item: subAdminPost.getPostIds()) {
            SysSubAdminPost i = new SysSubAdminPost();
            i.setUserId(subAdminPost.getUserId());
            i.setPostId(item);
            list.add(i);
        }
        return list;
    }

    /**
     * 子管理员角色相关的增删查
     */
    public List<SysRole> selectSubAdminRole(SysSubAdminRole subAdminRole) {
        return subAdminMapper.selectSubAdminRole(subAdminRole);
    };
    public int insertSubAdminRole(SysSubAdminRole subAdminRole) {
        return subAdminMapper.insertSubAdminRole(genSysSubAdminRoleList(subAdminRole));
    };
    public int deleteSubAdminRole(SysSubAdminRole subAdminRole) {
        return subAdminMapper.deleteSubAdminRole(genSysSubAdminRoleList(subAdminRole));
    };
    public List<SysSubAdminRole> genSysSubAdminRoleList (SysSubAdminRole subAdminRole) {
        List<SysSubAdminRole> list = new ArrayList<>();
        for(Long item: subAdminRole.getRoleIds()) {
            SysSubAdminRole i = new SysSubAdminRole();
            i.setUserId(subAdminRole.getUserId());
            i.setRoleId(item);
            list.add(i);
        }
        return list;
    }

    /**
     * 子管理员业务相关的增删查
     */
    public List<SysBusiness> selectSubAdminBusiness(SysSubAdminBusiness subAdminBusiness) {
        return subAdminMapper.selectSubAdminBusiness(subAdminBusiness);
    };
    public int insertSubAdminBusiness(SysSubAdminBusiness subAdminBusiness) {
        return subAdminMapper.insertSubAdminBusiness(genSysSubAdminBusinessList(subAdminBusiness));
    };
    public int deleteSubAdminBusiness(SysSubAdminBusiness subAdminBusiness) {
        return subAdminMapper.deleteSubAdminBusiness(genSysSubAdminBusinessList(subAdminBusiness));
    };
    public List<SysSubAdminBusiness> genSysSubAdminBusinessList (SysSubAdminBusiness subAdminBusiness) {
        List<SysSubAdminBusiness> list = new ArrayList<>();
        for(String item: subAdminBusiness.getBusinessIds()) {
            SysSubAdminBusiness i = new SysSubAdminBusiness();
            i.setUserId(subAdminBusiness.getUserId());
            i.setBusinessId(item);
            list.add(i);
        }
        return list;
    }
}
