package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.domain.entity.SysBusinessAuth;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessAuthOrgType;
import com.ruoyi.common.enums.SelectTreeNodeType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.SysBusinessAuthMapper;
import com.ruoyi.system.mapper.SysDeptMapper;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysBusinessAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysBusinessAuthServiceImpl implements ISysBusinessAuthService {

    @Autowired
    private SysBusinessAuthMapper businessAuthMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 根据权限信息查询业务权限列表
     */
    public List<SysBusinessAuth> selectBusinessAuthList(SysBusinessAuth sbam) {
        String orgType = sbam.getOrgType();
        String orgId = sbam.getOrgId();
        // 如果要查询继承的权限，并且是部门和人员，需要获取部门路径
        if( StringUtils.isNotNull(sbam.getExtendsOrg()) && StringUtils.isNotNull(orgId) && StringUtils.isNull(sbam.getDeptFullPathId()) ) {
            if(orgType.equals(BusinessAuthOrgType.DEPT.getValue())) {
                SysDept dept = deptMapper.selectDeptById(orgId);
                if(StringUtils.isNotNull(dept)) {
                    sbam.setDeptFullPathId(dept.getAncestors() + "," + dept.getDeptId());
                }
            }
            if(orgType.equals(BusinessAuthOrgType.USER.getValue())) {
                SysUser user = userMapper.selectUserById(orgId);
                SysDept dept = deptMapper.selectDeptById(user.getDeptId());
                if(StringUtils.isNotNull(user) && StringUtils.isNotNull(dept)) {
                    sbam.setDeptFullPathId(dept.getAncestors() + "," + dept.getDeptId());
                }
            }
        }
        return businessAuthMapper.selectBusinessAuthList(sbam);
    };

    /**
     * 新增业务权限
     */
    public int insertBusinessAuth(List<SysBusinessAuth> sysBusinessAuthList) {
        String userName = SecurityUtils.getLoginUser().getUser().getUserName();
        for(SysBusinessAuth sba: sysBusinessAuthList) {
            sba.setCreateBy(userName);
        }
        return businessAuthMapper.insertBusinessAuth(sysBusinessAuthList);
    };

    /**
     * 删除业务授权列表
     */
    public int deleteBusinessAuth(List<Long> businessAuthIds) {
        return businessAuthMapper.deleteBusinessAuth(businessAuthIds);
    };
}
